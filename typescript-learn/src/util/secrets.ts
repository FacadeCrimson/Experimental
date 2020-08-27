import dotenv from "dotenv";

dotenv.config({ path: ".env" });

export const SESSION_SECRET = process.env["SESSION_SECRET"];
export const MONGODB_URI = process.env["MONGODB_URI"];
export const FRONTEND = process.env["FRONTEND"];
export const RDS_PORT = parseInt(process.env["RDS_PORT"]);
export const RDS_HOST = process.env["RDS_HOST"];
export const RDS_PWD = process.env["RDS_PWD"];