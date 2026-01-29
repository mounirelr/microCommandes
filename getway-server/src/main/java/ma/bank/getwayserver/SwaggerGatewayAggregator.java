package ma.bank.getwayserver;

import jakarta.annotation.PostConstruct;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class SwaggerGatewayAggregator {

    private final RouteDefinitionLocator locator;
    private final SwaggerUiConfigProperties swaggerUiConfigProperties;
    @Value("${springdoc.api-docs.path}")
    private   String API_DOCS_SUFFIX ;

    public SwaggerGatewayAggregator(RouteDefinitionLocator locator,
                                    SwaggerUiConfigProperties swaggerUiConfigProperties) {
        this.locator = locator;
        this.swaggerUiConfigProperties = swaggerUiConfigProperties;
    }

    @PostConstruct
    public void init() {
        // Define the 'urls' set here
        Set<SwaggerUiConfigProperties.SwaggerUrl> urls = new HashSet<>();
        Set<String> excludedRoutes = Set.of("ReactiveCompositeDiscoveryClient_CONFIG-SERVER", "ReactiveCompositeDiscoveryClient_GETWAY-SERVER");

        locator.getRouteDefinitions()
                .filter(route ->!excludedRoutes.contains(route.getId())) // optional: skip gateway and config-server
                .toStream()
                .forEach(route -> {
                    System.out.println(route.getId());
                    String resourceName = cleanRouteName(route.getId());

                    // Get the Path predicate
                    String location = route.getPredicates().stream()
                            .filter(p -> "Path".equalsIgnoreCase(p.getName()))
                            .findFirst()
                            .map(p -> p.getArgs().get("pattern")) // usually key is "pattern"
                            .orElse("/**");

                    // Replace /** with API_DOCS_SUFFIX
                    location = location.replace("/**", API_DOCS_SUFFIX);

                    urls.add(new SwaggerUiConfigProperties.SwaggerUrl(resourceName, location, resourceName));
                });

        //Set the URLs dynamically
        swaggerUiConfigProperties.setUrls(urls);
    }
    private String cleanRouteName(String routeId) {
        System.out.println(routeId);
        if (routeId.contains("_")) {
            return routeId.substring(routeId.indexOf("_") + 1);
        }
        return routeId;
    }
}