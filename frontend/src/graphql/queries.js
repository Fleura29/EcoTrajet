// contient les requetes graphql 

import { gql } from '@apollo/client';

export const REGISTER_MUTATION = gql`
  mutation Register($input: CreateUserDto!) {
    createUser(input: $input) {
      id
      login
      type
    }
  } 
`;

export const LOGIN_MUTATION = gql`
  mutation Login($input: LoginDto!) {
    login(input: $input)
  }
`;

export const COMPUTE_ITINERARY = gql`
  query computeItinerary($input: ComputeItineraryDto!) {
    computeItinerary(input: $input) {
      id
      distance
      emissions
      time
      user {
        id
      }
      points {
        lats
        lons
      }
    }
  }
`;
