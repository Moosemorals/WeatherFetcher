# Weather data fetch library

[World Weather Online](https://www.worldweatheronline.com) provide
 a weather data with a free API. This library provides a Java 
interface to that API.

You can register for the free API at [https://developer.worldweatheronline.com/auth/register](https://developer.worldweatheronline.com/auth/register). This
will give you an API key that needs to be included with every request.

Requests are for a particular location. Location can be a city name, a UK 
or Canadian postcode, a US zipcode, an IPv4 address in dotted quad notation,
or a Longitude,Latitude pair (in decimal format, separated by commas).

The free API provides current weather conditions and forecast data for up to 
five days at a resolution of up to three hours. (They also provide a paid
API but I can't afford to play with that)

The free API is limited to 250 requests/day, 5 requests/second. This library
does nothing to enforce those limits, but does report your usage.

# Example

    final String API_KEY = "...";
    final String LOCATION = "..."; // Name of a city, a postcode, a zipcode, an IPv4 address, or a latitude,longitude pair

    FetchResult result = new Fetcher.Builder()
        .setApiKey(API_KEY)
        .setLocation(LOCATION) 
        .build()
        .fetch();

    if (result.isSuccess()) {
        WeatherReport report = result.getWeather();
        System.out.println("The temprature in " + LOCATION + " is " + report.getCurrent().getTempC() + "\u00B0C";
    } else {
        ErrorReport report = result.getError();
        System.out.println("Sorry there was a problem: " + report.getMessage());
    }

# Javadoc

[Javadoc](http://moosemorals.github.io/WeatherFetcher/apidocs/index.html) for the library.

# Licence

My code is under the [MIT licence](LICENCE). Use of the API data is covered by their 
[terms and conditions](http://www.worldweatheronline.com/api/free-api-terms.aspx)
and requires a link back to their site.
