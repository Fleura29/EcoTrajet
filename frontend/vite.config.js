import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import path from 'node:path'; 

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/graphql': {
        target: 'http://localhost:4000',
        changeOrigin: true,
      },
    },
  },
  resolve: {},
  test: {
    environment: 'jsdom',
    globals: true,
    setupFiles: './setupTests.js',
  },
  optimizeDeps: {
    dedupe: ['react', 'react-dom'],
  },
});
