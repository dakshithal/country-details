import { Box, Card, CardContent, Typography } from '@mui/material'

export interface Country {
  name: string;
  country_code: string;
  capital: string;
  population: number;
  flag_file_url: string;
}

export interface CountryDetailsProps {
  country?: Country;
}

export const CountryDetails = ({ country }: CountryDetailsProps) => {
  return (
    <Box
      id='country-details'
      style={{
        flex: 2,
        marginLeft: '20px',
      }}
    >
      <Typography variant='h2'>
        {country ? country.name : 'Please select a country to view details'}
      </Typography>
      {
        country
        ? <Card
            style={{
              width: '800px',
              maxWidth: '800px',
            }}
          >
            <CardContent
              style={{
                display: 'flex',
                backgroundColor: '#FBF8F5'
              }}
            >
              <img
                width='500px'
                height='300px'
                src={country.flag_file_url}
                alt={`${country.name} flag is not available`}
                onError={({ currentTarget }) => {
                  currentTarget.onerror = null;
                  currentTarget.src=`https://via.placeholder.com/500x300.png?text=${country.name} flag is not available`
                }}
              />
              <Box style={{ marginLeft: '20px' }}>
                <Typography variant='subtitle2'>
                  Country Code
                </Typography>
                <Typography variant='h5'>
                  {country.country_code}
                </Typography>
                <Typography variant='subtitle2'>
                  Capital
                </Typography>
                <Typography variant='h5'>
                  {country.capital}
                </Typography>
                <Typography variant='subtitle2'>
                  Population
                </Typography>
                <Typography variant='h5'>
                  {country.population ? country.population.toLocaleString() : 'Not Available'}
                </Typography>
              </Box>
            </CardContent>
          </Card>
        : null
      }
    </Box>
  )
}