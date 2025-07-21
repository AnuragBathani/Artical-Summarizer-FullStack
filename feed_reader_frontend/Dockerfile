# Stage 1: Build the frontend application
# Use a Node.js image to install dependencies and build the project.
# The 'alpine' variant is chosen for a smaller base image size.
FROM node:20-alpine AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy package.json and package-lock.json first.
# This allows Docker to cache the dependency installation step.
COPY package*.json ./

# Install project dependencies.
# 'npm ci' is preferred over 'npm install' in CI/CD environments
# as it ensures a clean install based on package-lock.json.
RUN npm ci

# Copy the rest of the application source code (including src, public, vite.config.js, etc.)
COPY . .

# Build the frontend application for production using Vite.
# Vite will compile your TypeScript/JavaScript, bundle assets, and optimize them.
# The output will be placed in the 'dist' directory by default, as per Vite's standard.
RUN npm run build

# Stage 2: Serve the built application with Nginx
# Use a lightweight Nginx image to serve the static files.
FROM nginx:alpine

# Remove the default Nginx configuration file to replace it with our custom one.
RUN rm /etc/nginx/conf.d/default.conf

# Copy the custom Nginx configuration file into the container.
# This file is essential for correctly serving a single-page application.
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copy the built static files from the 'builder' stage into the Nginx web root.
# Vite's default output directory is 'dist'.
COPY --from=builder /app/dist /usr/share/nginx/html

# Expose port 80, as Nginx typically listens on port 80 by default for HTTP traffic.
EXPOSE 80

# The default command for the nginx:alpine image starts Nginx in the foreground.
# This is suitable for Docker containers.
CMD ["nginx", "-g", "daemon off;"]


