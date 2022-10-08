import { useEffect, useState } from 'react';
import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';
import { Box } from '@mui/material';
import { CountryList, CountryListItem } from './components/CountryList';
import { Country, CountryDetails } from './components/CountryDetails';

function App() {
  const [countries, setCountries] = useState<CountryListItem[]>([]);
  const [selectedCountry, setSelectedCountry] = useState<CountryListItem>();
  const [selectedCountryDetails, setSelectedCountryDetails] = useState<Country>();

  useEffect(() => {
    fetch("http://localhost:8080/v1/countries")
    .then((response) => {
      return response.json();
    })
    .then((countriesResponse: { countries: CountryListItem[]}) => {
      setCountries(countriesResponse.countries);
    })
    .catch((e) => {
      console.error("Error while fetching country list", e);
    })
  }, []);

  useEffect(() => {
    if (!selectedCountry) {
      return;
    }
    fetch(`http://localhost:8080/v1/countries/${selectedCountry.name}`)
    .then((response) => {
      return response.json();
    })
    .then((countryDetails: Country) => {
      setSelectedCountryDetails(countryDetails);
    })
    .catch((e) => {
      console.error(`Error while fetching country details for ${selectedCountry.name}`, e);
    })
  }, [selectedCountry]);

  return (
    <Box>
      <div style={{ display: 'flex'}}>
          <CountryList
            countries={countries}
            setSelectedCountry={setSelectedCountry}
          />
          <CountryDetails
            country={selectedCountryDetails}
          />
      </div>
    </Box>
  );
}

export default App;
