import React from "react";
import CarBrandCheckboxes from "../UI/CarBrandCheckboxes";
import TransportCheckboxes from "../UI/TransportCheckboxes";

const TransportForm = ({ selectedTransport, setSelectedTransport, setSelectedCarBrands }) => {
  const handleTransportChange = (newSelection) => {
    setSelectedTransport(newSelection);
  };

  const handleCarBrandChange = (newSelection) => {
    setSelectedCarBrands(newSelection);
  };

  return (
    <div className="space-y-6">
      <TransportCheckboxes 
        initialSelection={selectedTransport}
        onSelectionChange={handleTransportChange}
      />
      
      {selectedTransport.car && (
        <CarBrandCheckboxes 
          onSelectionChange={handleCarBrandChange}
        />
      )}
    </div>
  );
};

export default TransportForm;
