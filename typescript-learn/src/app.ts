import express from "express";
import compression from "compression";
import session from "express-session";
import bodyParser from "body-parser";
import lusca from "lusca";
import mongo from "connect-mongo";
import flash from "express-flash";
import path from "path";
import mongoose from "mongoose";
import passport from "passport";
import bluebird from "bluebird";
import cors from "cors";
import redis from "redis";
import { MONGODB_URI, SESSION_SECRET, FRONTEND, RDS_PORT, RDS_HOST, RDS_PWD } from "./util/secrets";

const MongoStore = mongo(session);

// Controllers (route handlers)
import * as homeController from "./controllers/home";
import * as userController from "./controllers/user";
import * as apiController from "./controllers/api";
import * as contactController from "./controllers/contact";


// API keys and Passport configuration
import * as passportConfig from "./config/passport";

// Create Express server
const app = express();

// Connect to MongoDB
const mongoUrl = MONGODB_URI;
mongoose.Promise = bluebird;

mongoose.connect(mongoUrl, { useNewUrlParser: true, useCreateIndex: true, useUnifiedTopology: true } ).then(
    () => { /** ready to use. The `mongoose.connect()` promise resolves to undefined. */ },
).catch(err => {
    console.log(`MongoDB connection error. Please make sure MongoDB is running. ${err}`);
    // process.exit();
});

// Express configuration
app.set("port", process.env.PORT || 3000);
app.set("views", path.join(__dirname, "../views"));
app.set("view engine", "pug");

// Setting cors
const options: cors.CorsOptions = {
    allowedHeaders: [
      "Origin",
      "X-Requested-With",
      "Content-Type",
      "Accept",
      "X-Access-Token",
    ],
    credentials: true,
    methods: "GET,HEAD,OPTIONS,PUT,PATCH,POST,DELETE",
    origin: FRONTEND,
    preflightContinue: false,
  };
app.use(cors(options));

// Setting redis
const client = redis.createClient(RDS_PORT, RDS_HOST, {no_ready_check: true});
client.auth(RDS_PWD, function (err) {
    if(err) {throw err;};
});
client.on("error", function (err) {
    console.log("Error " + err);
});
client.on("connect", function() {
    console.log("Connected to Redis");
});

app.use(compression());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(session({
    resave: true,
    saveUninitialized: true,
    secret: SESSION_SECRET,
    store: new MongoStore({
        url: mongoUrl,
        autoReconnect: true
    })
}));
app.use(passport.initialize());
app.use(passport.session());
app.use(flash());
app.use(lusca.xframe("SAMEORIGIN"));
app.use(lusca.xssProtection(true));
app.use((req, res, next) => {
    res.locals.user = req.user;
    next();
});
app.use((req, res, next) => {
    // After successful login, redirect back to the intended page
    if (!req.user &&
    req.path !== "/login" &&
    req.path !== "/signup" &&
    !req.path.match(/^\/auth/) &&
    !req.path.match(/\./)) {
        req.session.returnTo = req.path;
    } else if (req.user &&
    req.path == "/account") {
        req.session.returnTo = req.path;
    }
    next();
});

app.use(
    express.static(path.join(__dirname, "public"), { maxAge: 31557600000 })
);

/**
 * Primary app routes.
 */
app.get("/", homeController.index);
app.get("/login", userController.getLogin);
app.post("/login", userController.postLogin);
app.get("/logout", userController.logout);
app.get("/forgot", userController.getForgot);
app.post("/forgot", userController.postForgot);
app.get("/reset/:token", userController.getReset);
app.post("/reset/:token", userController.postReset);
app.get("/signup", userController.getSignup);
app.post("/signup", userController.postSignup);
app.get("/contact", contactController.getContact);
app.post("/contact", contactController.postContact);
app.get("/account", passportConfig.isAuthenticated, userController.getAccount);
app.post("/account/profile", passportConfig.isAuthenticated, userController.postUpdateProfile);
app.post("/account/password", passportConfig.isAuthenticated, userController.postUpdatePassword);
app.post("/account/delete", passportConfig.isAuthenticated, userController.postDeleteAccount);
app.get("/account/unlink/:provider", passportConfig.isAuthenticated, userController.getOauthUnlink);

/**
 * API examples routes.
 */
app.get("/api", apiController.getApi);
app.get("/api/facebook", passportConfig.isAuthenticated, passportConfig.isAuthorized, apiController.getFacebook);

/**
 * OAuth authentication routes. (Sign in)
 */
app.get("/auth/facebook", passport.authenticate("facebook", { scope: ["email", "public_profile"] }));
app.get("/auth/facebook/callback", passport.authenticate("facebook", { failureRedirect: "/login" }), (req, res) => {
    res.redirect(req.session.returnTo || "/");
});

export default app;

// const methodOverride = require('method-override')
// const cookieParser = require('cookie-parser')
// app.use(cookieParser())
// app.use(methodOverride())

// require('./routes')(app)

// //error handler
// app.use(function(req, res, next) {
//   next(new AppError(`Can't find ${req.originalUrl} on this server!`, 404))
// })

// app.use(function (err, req, res, next) {
//     err.statusCode = err.statusCode || 500;
//     err.status = err.status || 'error';
  
//     res.status(err.statusCode).json({
//       status: err.status,
//       message: err.message
//     })
// })



// redisFunctions=require('./functions/checkredis')

// const express = require('express')
// router = express.Router()

// //error catching function
// const catchAsync = fn => {
//   return (req, res, next) => {
//     fn(req, res, next).catch(next)
//   }
// }

// module.exports = function(app){
//   router.get('/', function(req,res){res.send("Hello World!")})

//   app.use('/', router)
// }


// class AppError extends Error {
//     constructor(message, statusCode) {
//       super(message)
  
//       this.statusCode = statusCode
//       this.status = `${statusCode}`.startsWith('4') ? 'fail' : 'error'
//       this.isOperational = true
      
//       Error.captureStackTrace(this, this.constructor)
//     }
//   }

//   module.exports=AppError