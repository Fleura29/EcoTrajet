
/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    './src/**/*.{js,jsx,ts,tsx}',
  ],
  theme: {
    extend: {
      colors: {
        golden: '#b28f4f',
        grayd9: '#D9D9D9',
        graybb: '#BBBBBB'
      },
      zIndex: {
        '1001': '1001'
      },
    },
  },
  plugins: [],
}