package digital.erben.movies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.UUID;

/**
 * The CartService class provides methods to interact with a cart in a remote service.
 */
public class CartService {

    private final String host;
    private final int port;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CartService(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Retrieves a cart with the specified cartId and sessionId.
     *
     * @param cartId    The UUID of the cart to retrieve.
     * @param sessionId The UUID of the session.
     * @return An Optional containing the retrieved Cart, or empty if the cart is not found.
     * @throws RuntimeException if there is an error processing the response body.
     */
    Optional<Cart> retrieveCartWithId(UUID cartId, UUID sessionId) {
        URI uri = URI.create(baseUrl() + "?cartId=" + cartId.toString());
        CookieManager manager = setupCookieManager(sessionId, uri);
        try (var httpClient = HttpClient.newBuilder().cookieHandler(manager).build()) {
            var result = httpClient.sendAsync(
                HttpRequest.newBuilder(uri).GET().build(),
                HttpResponse.BodyHandlers.ofString()
            );
            var awaited = result.join();
            if (awaited.statusCode() == 404) {
                return Optional.empty();
            }
            var cart = objectMapper.readValue(awaited.body(), Cart.class);
            return Optional.ofNullable(cart);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a new cart.
     *
     * @param cart      The Cart to be created.
     * @param sessionId The UUID of the session.
     * @return The created Cart.
     * @throws RuntimeException If there is an error processing the response body.
     */
    public Cart create(Cart cart, UUID sessionId) {
        URI uri = URI.create(baseUrl());
        CookieManager manager = setupCookieManager(sessionId, uri);
        try (var httpClient = HttpClient.newBuilder().cookieHandler(manager).build()) {
            var body = objectMapper.writeValueAsString(cart);
            var result = httpClient.sendAsync(
                HttpRequest
                    .newBuilder(uri)
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build(),
                HttpResponse.BodyHandlers.ofString()
            );
            var awaited = result.join();
            return objectMapper.readValue(awaited.body(), Cart.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a cart with the specified cartId and sessionId.
     *
     * @param cartId The UUID of the cart to delete.
     * @param sessionId The UUID of the session.
     */
    public void delete(UUID cartId, UUID sessionId) {
        URI uri = URI.create(baseUrl() + "?cartId=" + cartId.toString());
        CookieManager manager = setupCookieManager(sessionId, uri);
        try (var httpClient = HttpClient.newBuilder().cookieHandler(manager).build()) {
            var result = httpClient.sendAsync(
                HttpRequest.newBuilder(uri).DELETE().build(),
                HttpResponse.BodyHandlers.ofString()
            );
            result.join();
        }
    }

    /**
     * Sets up a CookieManager with a session cookie for the given session ID and URI.
     * The session cookie is added to the CookieStore associated with the CookieManager.
     *
     * @param sessionId The UUID of the session.
     * @param uri       The URI for which the session cookie is being set up.
     * @return The initialized CookieManager instance.
     */
    private static CookieManager setupCookieManager(UUID sessionId, URI uri) {
        HttpCookie sessionCookie = new HttpCookie("sessionId", sessionId.toString());
        sessionCookie.setPath("/");
        CookieManager manager = new CookieManager();
        manager.getCookieStore().add(uri, sessionCookie);
        return manager;
    }

    private String baseUrl() {
        return "http://" + host + ":" + port + "/view/cart";
    }
}
