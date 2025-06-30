# Stage 1: Base image
FROM oven/bun:1.2.17 as base

WORKDIR /app

# Copy package files
COPY bun.lock ./
COPY package.json ./

# Install dependencies using Bun
RUN bun install --frozen-lockfile

# Copy source code
COPY . .

# Build Nuxt app
RUN bun run build

# Stage 2: Production image
FROM oven/bun:1.2.17 as prod

WORKDIR /app

# Copy built app from base
COPY --from=base /app/.output .output
COPY --from=base /app/node_modules node_modules
COPY --from=base /app/package.json ./

# Expose the port
EXPOSE 3000

# Run Nuxt app using Bun
CMD ["bun", "start"]
