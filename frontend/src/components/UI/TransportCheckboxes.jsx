import React, { useState, useEffect } from "react";
import Checkbox from './Checkbox';

const TransportCheckboxes = ({ initialSelection, onSelectionChange }) => {
  const transportOptions = [
    { key: "car", label: "Voiture" },
    { key: "bicycle", label: "Vélo " },
    { key: "pedestrian", label: "Marche" },
  ];

  const [selectedTransport, setSelectedTransport] = useState(() => {
    return initialSelection || Object.fromEntries(transportOptions.map(option => [option.key, true]));
  });

  useEffect(() => {
    if (onSelectionChange) {
      onSelectionChange(selectedTransport);
    }
  }, [selectedTransport]);

  useEffect(() => {
    if (initialSelection) {
      setSelectedTransport(initialSelection);
    }
  }, [initialSelection]);

  const handleTransportChange = (key) => {
    const newSelection = {
      ...selectedTransport,
      [key]: !selectedTransport[key],
    };
    setSelectedTransport(newSelection);
    onSelectionChange(newSelection);
  };

  return (
    <div>
      <h1 className="font-semibold text-lg mb-2 text-black">Mode de transport</h1>
      <div className="ms-1 space-y-1">
        {transportOptions.map(option => (
          <Checkbox
            key={option.key}
            label={option.label}
            checked={!!selectedTransport[option.key]}
            onChange={() => handleTransportChange(option.key)}
          />
        ))}
      </div>
    </div>
  );
};

export default TransportCheckboxes;
