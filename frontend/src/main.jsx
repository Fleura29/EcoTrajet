import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';


// import { MockedProvider } from '@apollo/client/testing';
// import { mocks } from './graphql/mocks';

// ReactDOM.createRoot(document.getElementById('root')).render(
//   <React.StrictMode>
//     <MockedProvider mocks={mocks} addTypename={false}>
//       <App />
//     </MockedProvider>
//   </React.StrictMode>
// );


///// 

import { ApolloProvider } from '@apollo/client';
import client from './graphql/client';

ReactDOM.createRoot(document.getElementById('root')).render(
   <React.StrictMode>
     <ApolloProvider client={client}>
       <App />
     </ApolloProvider>
   </React.StrictMode>
);

