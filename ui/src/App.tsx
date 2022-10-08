import React from 'react';
import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';
import { Container } from '@mui/material';
import { CountryList } from './components/CountryList';
import { Country } from './components/Country';

function App() {
  return (
    <Container>
      <div style={{ display: 'flex'}}>
          <CountryList />
          <Country />
      </div>
    </Container>
  );
}

export default App;
