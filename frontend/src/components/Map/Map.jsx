import React, { useEffect, useRef } from 'react';
import ReactDOM from 'react-dom/client';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import { FaFlag } from "react-icons/fa6";
import { IoMdPin } from "react-icons/io";

function Map({ resetKey, routePoints }) {
  const mapRef = useRef(null);
  const mapInstanceRef = useRef(null);
  const polylineRef  = useRef(null);
  const startMarkerRef = useRef(null);
  const endMarkerRef = useRef(null);

  useEffect(() => {
    if (!mapInstanceRef.current) {
      const map = L.map(mapRef.current).setView([46.731192, 2.810232], 6);
      L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '© OpenStreetMap'
      }).addTo(map);
      mapInstanceRef.current = map;
    }

    return () => {
      if (mapInstanceRef.current) {
        mapInstanceRef.current.remove();
        mapInstanceRef.current = null;
      }
    };
  }, []);


  useEffect(() => {
    if (polylineRef .current) {
      polylineRef .current.remove();
      polylineRef .current = null;
    }
    if (startMarkerRef.current) {
      startMarkerRef.current.remove();
      startMarkerRef.current = null;
    }
    if (endMarkerRef.current) {
      endMarkerRef.current.remove();
      endMarkerRef.current = null;
    }
  }, [resetKey]);

  useEffect(() => {
    if (routePoints && routePoints.length > 1 && mapInstanceRef.current) {
      const map = mapInstanceRef.current;
      const latLngs = routePoints.map(pt => L.latLng(pt.lat, pt.lng));

      const polyline = L.polyline(latLngs, {
        color: 'blue',
        weight: 4,
        opacity: 0.7,
      }).addTo(map);

      map.fitBounds(L.latLngBounds(latLngs), { padding: [50, 50] });

      polylineRef .current = {
        remove: () => map.removeLayer(polyline),
      };

      function createReactIconMarker(position, IconComponent) {
        const container = document.createElement('div');

        const icon = L.divIcon({
          className: '', 
          html: container,
          iconSize: [24, 24],
          iconAnchor: [12, 24],
        });

        const marker = L.marker(position, { icon }).addTo(map);
        const root = ReactDOM.createRoot(container);
        root.render(<IconComponent size={24} color={'black'} />);

        return marker;
      }

      const startCoords = latLngs[0];
      const endCoords = latLngs[latLngs.length - 1];

      startMarkerRef.current = createReactIconMarker(startCoords, IoMdPin);
      endMarkerRef.current = createReactIconMarker(endCoords, FaFlag);
    }
  }, [routePoints]);

  return <div ref={mapRef} style={{ width: '100%', height: '100vh', zIndex: 0 }} />;
}

export default Map;
