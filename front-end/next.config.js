/** @type {import('next').NextConfig} */
const path = require('path')
const nextConfig = {
  reactStrictMode: true,
  sassOptions: {
    includePaths: [path.join(__dirname, 'styles')],
  },
  async redirects() {
    return [
      {
        source: "/",
        destination: "/main",
        permanent: false
      }
    ]
  }
  // async rewrites() {
  //   return [
  //     {
  //       source: '/api/:path*',
  //       destination: 'http://193.123.230.252:8080/:path*',
  //     },
  //   ]
  // },
}

module.exports = nextConfig