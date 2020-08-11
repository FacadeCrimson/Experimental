const { GraphQLServer } = require('graphql-yoga')
const { PrismaClient } = require('@prisma/client')

// 2
const resolvers = {
    Query: {
      info: () => `This is the API of a Hackernews Clone`,
      // 2
      feed: async(parent,args,context) => {
          return context.prisma.link.findMany()
      }
    },
    // 3
    Mutation: {
        // 2
        post: (parent, args,context,info) => {
           const newLink=context.prisma.link.create({
               data:{
                   url:args.url,
                   description:args.description,
               },
           })
           return newLink
        }
    }
  }

// 3
const prisma = new PrismaClient()

const server = new GraphQLServer({
  typeDefs: './src/schema.graphql',
  resolvers,
  context: {
    prisma, 
  }
})
server.start(() => console.log(`Server is running on http://localhost:4000`))