// contient les données mock

import {
  REGISTER_MUTATION,
  LOGIN_MUTATION,
  COMPUTE_ITINERARY
} from './queries';

// Données constantes pour les adresses simulées
const SOURCE_ADDRESS = "Nauti, Contié, Canens, Muret, Haute-Garonne, Occitanie, France métropolitaine, 31310, France";
const TARGET_ADDRESS = "Centre commercial Porte-des-Alpes, Boulevard André Boulloche, Village, Saint-Priest, Lyon, Métropole de Lyon, Rhône, Auvergne-Rhône-Alpes, France métropolitaine, 69800, France";

const COORDS = {
  sourceLat: 43.2167967,
  sourceLon: 1.3332403,
  targetLat: 45.7204102,
  targetLon: 4.9242457 //4598967
};

// Liste des mocks utilisés par MockedProvider
export const mocks = [
  // Mock inscription
  {
    request: {
      query: REGISTER_MUTATION,
      variables: {
        input: {
          login: "linda@test.com",
          password: "azerty",
          type: "UTILISATEUR"
        }
      }
    },
    result: {
      data: {
        createUser: {
          id: "1",
          login: "linda@test.com",
          type: "UTILISATEUR"
        }
      }
    }
  },

  // Mock login
  {
    request: {
      query: LOGIN_MUTATION,
      variables: {
        input: {
          login: "linda@test.com",
          password: "azerty"
        }
      }
    },
    result: {
      data: {
        login: "fake-token-1"
      }
    }
  },

  // Mocks pour chaque type de transport avec userID null (non connecté)
  ...["walking", "public_transport", "electric_scooter", "train", "car", "electric_bike"].map((type, index) => ({
    request: {
      query: COMPUTE_ITINERARY,
      variables: {
        input: {
          sourceText: SOURCE_ADDRESS,
          targetText: TARGET_ADDRESS,
          ...COORDS,
          vehicleType: type,
          userID: null
        }
      }
    },
    result: {
      data: {
        computeItinerary: {
          id: `it-${type}-guest-${index}`,
          distance: 5000 + index * 100,
          emissions: type === "walking" ? 0 : 50 + index * 20,
          duration: 1800 + index * 300,
          user: null,
          startCoordinates: {
          lat: COORDS.sourceLat + index * 0.01,
          lng: COORDS.sourceLon + index * 0.01
        },
        endCoordinates: {
          lat: COORDS.targetLat + index * 0.01,
          lng: COORDS.targetLon + index * 0.01
        } 
        }
      }
    }
  })),

  // Mocks pour chaque type de transport avec userID 1 (connecté)
  ...["walking", "public_transport", "electric_scooter", "train", "car", "electric_bike"].map((type, index) => ({
    request: {
      query: COMPUTE_ITINERARY,
      variables: {
        input: {
          sourceText: SOURCE_ADDRESS,
          targetText: TARGET_ADDRESS,
          ...COORDS,
          vehicleType: type,
          userID: 1
        }
      }
    },
    result: {
      data: {
        computeItinerary: {
          id: `it-${type}-1-${index}`,
          distance: 5000 + index * 100,
          emissions: type === "walking" ? 0 : 60 + index * 25,
          duration: 1900 + index * 320,
          user: { id: "1" },
          startCoordinates: {
          lat: COORDS.sourceLat + index * 0.01,
          lng: COORDS.sourceLon + index * 0.01
        },
        endCoordinates: {
          lat: COORDS.targetLat + index * 0.01,
          lng: COORDS.targetLon + index * 0.01
        } 
        }
      }
    }
  }))
];
