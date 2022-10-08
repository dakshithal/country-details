import { Card, CardContent, Typography } from "@mui/material"
import { Container } from "@mui/system"

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
    <Container id="country-details">
      <Typography variant="h2">
        Country Details
      </Typography>
      {
        country
        ? <Card>
            <CardContent>
              <Typography variant="h3">
                {country.name}
              </Typography>
              <img
                width="100px"
                height="75px"
                src={country.flag_file_url}
                alt={`flag of ${country.name}`}
              />
            </CardContent>
          </Card>
        : <Card>
            <CardContent>
              <Typography variant="h3">
                Please select a country to view details
              </Typography>
            </CardContent>
          </Card>
      }

    </Container>
  )
}