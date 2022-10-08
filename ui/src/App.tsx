import React, { useState } from 'react';
import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';
import { Container } from '@mui/material';
import { CountryList, CountryListItem } from './components/CountryList';
import { Country, CountryDetails } from './components/CountryDetails';

function App() {
  const [countries, setCountries] = useState<CountryListItem[]>([]);
  const [selectedCountry, setSelectedCountry] = useState<CountryListItem>();
  const [selectedCountryDetails, setSelectedCountryDetails] = useState<Country>();

  return (
    <Container>
      <div style={{ display: 'flex'}}>
          <CountryList
            countries={countries}
            setSelectedCountry={setSelectedCountry}
          />
          <CountryDetails
            country={selectedCountryDetails}
          />
      </div>
    </Container>
  );
}

export default App;
