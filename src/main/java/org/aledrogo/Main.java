package org.aledrogo;

import org.aledrogo.entity.*;
import org.aledrogo.repository.*;
import org.aledrogo.service.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public final UserRepository userRepository = new MemoryUserRepository();
    public final OfferRepository offerRepository = new MemoryOfferRepository();
    public final OfferReportRepository offerReportRepository = new MemoryOfferReportRepository();
    public final OrderReviewRepository orderReviewRepository = new MemoryOrderReviewRepository();
    public final OrderRepository orderRepository = new MemoryOrderRepository();

    public final AuthService authService = new AuthService(userRepository);
    public final OfferSearchService offerSearchService = new DummyOfferOfferSearchService(offerRepository);
    public final OfferService offerService = new OfferService(offerRepository, offerReportRepository);
    public final OrderService orderService = new OrderService(orderRepository, orderReviewRepository, offerRepository);
    public final CartService cartService = new CartService();

    private final Scanner scanner = new Scanner(System.in);
    private User loggedInUser = null;

    public static void main(String[] args) {
        new Main().run();
    }

    private void seedUsers() throws Exception {
        authService.register(new Customer("customer@example.com", "password"));
        authService.register(new Seller("seller@example.com", "password", "Jan", "Kowalski", "123456789"));
        authService.register(new Moderator("moderator@example.com", "password", "Adam", "Kowalski"));
    }

    private void run() {
        try {
            seedUsers();
        } catch (Exception e) {
            System.out.println("Błąd inicjalizacji: " + e.getMessage());
        }

        while (true) {
            if (loggedInUser == null) {
                loginMenu();
            } else if (loggedInUser instanceof Moderator) {
                moderatorMenu();
            } else if (loggedInUser instanceof Seller) {
                sellerMenu();
            } else if (loggedInUser instanceof Customer) {
                customerMenu();
            }
        }
    }

    private void loginMenu() {
        System.out.println("=== Aledrogo ===");
        System.out.println("1. Zaloguj się");
        System.out.println("2. Zarejestruj się jako klient");
        System.out.println("3. Zarejestruj się jako sprzedawca");
        System.out.println("0. Wyjdź");
        System.out.print("> ");

        switch (scanner.nextLine().trim()) {
            case "1" -> login();
            case "2" -> registerCustomer();
            case "3" -> registerSeller();
            case "0" -> { System.out.println("Do widzenia!"); System.exit(0); }
            default  -> System.out.println("Nieznana opcja.\n");
        }
    }

    private void login() {
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Hasło: ");
        String password = scanner.nextLine().trim();

        try {
            loggedInUser = authService.login(email, password);
            System.out.println("\nZalogowano jako: " + loggedInUser.getEmail() + " (" + roleName(loggedInUser) + ")\n");
        } catch (Exception e) {
            System.out.println("Błąd: " + e.getMessage() + "\n");
        }
    }

    private void registerCustomer() {
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Hasło: ");
        String password = scanner.nextLine().trim();

        try {
            authService.register(new Customer(email, password));
            System.out.println("Rejestracja zakończona pomyślnie. Możesz się teraz zalogować.\n");
        } catch (Exception e) {
            System.out.println("Błąd: " + e.getMessage() + "\n");
        }
    }

    private void registerSeller() {
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Hasło: ");
        String password = scanner.nextLine().trim();
        System.out.print("Imię: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Nazwisko: ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Numer telefonu: ");
        String phoneNumber = scanner.nextLine().trim();

        try {
            authService.register(new Seller(email, password, firstName, lastName, phoneNumber));
            System.out.println("Rejestracja zakończona pomyślnie. Możesz się teraz zalogować.\n");
        } catch (Exception e) {
            System.out.println("Błąd: " + e.getMessage() + "\n");
        }
    }

    private String roleName(User user) {
        if (user instanceof Moderator) return "Moderator";
        if (user instanceof Seller)    return "Sprzedawca";
        if (user instanceof Customer)  return "Klient";
        return "Użytkownik";
    }

    private void customerMenu() {
        System.out.println("=== Aledrogo — Klient (" + loggedInUser.getEmail() + ") ===");
        System.out.println("1. Szukaj ofert");
        System.out.println("2. Dodaj ofertę do koszyka");
        System.out.println("3. Usuń ofertę z koszyka");
        System.out.println("4. Wyświetl koszyk");
        System.out.println("5. Wyczyść koszyk");
        System.out.println("6. Złóż zamówienie");
        System.out.println("7. Wyświetl moje zamówienia");
        System.out.println("8. Potwierdź odbiór zamówienia");
        System.out.println("9. Dodaj opinię");
        System.out.println("0. Wyloguj się");
        System.out.print("> ");

        switch (scanner.nextLine().trim()) {
            case "1" -> searchOffers();
            case "2" -> addToCart();
            case "3" -> removeFromCart();
            case "4" -> viewCart();
            case "5" -> clearCart();
            case "6" -> placeOrder();
            case "7" -> viewMyOrders();
            case "8" -> completeOrder();
            case "9" -> createOrderReview();
            case "0" -> logout();
            default  -> System.out.println("Nieznana opcja.");
        }
        System.out.println();
    }

    private void sellerMenu() {
        System.out.println("=== Aledrogo — Sprzedawca (" + loggedInUser.getEmail() + ") ===");
        System.out.println("1. Szukaj ofert");
        System.out.println("2. Dodaj ofertę");
        System.out.println("3. Edytuj ofertę");
        System.out.println("4. Usuń ofertę");
        System.out.println("0. Wyloguj się");
        System.out.print("> ");

        switch (scanner.nextLine().trim()) {
            case "1" -> searchOffers();
            case "2" -> createOffer();
            case "3" -> updateOffer();
            case "4" -> deleteOffer();
            case "0" -> logout();
            default  -> System.out.println("Nieznana opcja.");
        }
        System.out.println();
    }

    private void moderatorMenu() {
        System.out.println("=== Aledrogo — Moderator (" + loggedInUser.getEmail() + ") ===");
        System.out.println("1. Szukaj ofert");
        System.out.println("2. Usuń ofertę");
        System.out.println("3. Zgłoś ofertę");
        System.out.println("4. Usuń opinię");
        System.out.println("0. Wyloguj się");
        System.out.print("> ");

        switch (scanner.nextLine().trim()) {
            case "1" -> searchOffers();
            case "2" -> deleteOffer();
            case "3" -> reportOffer();
            case "4" -> deleteOrderReview();
            case "0" -> logout();
            default  -> System.out.println("Nieznana opcja.");
        }
        System.out.println();
    }

    private void logout() {
        System.out.println("Wylogowano.");
        loggedInUser = null;
    }

    private void searchOffers() {
        System.out.print("Fraza: ");
        String query = scanner.nextLine().trim();

        ArrayList<Offer> results = offerSearchService.match(query);
        if (results.isEmpty()) {
            System.out.println("Nie znaleziono ofert.");
        } else {
            System.out.println("Wyniki (" + results.size() + "):");
            results.forEach(this::printOffer);
        }
    }

    private void createOffer() {
        System.out.print("Nazwa: ");
        String name = scanner.nextLine().trim();
        System.out.print("Opis: ");
        String description = scanner.nextLine().trim();
        Double price = askDouble("Cena: ");
        if (price == null) return;
        Integer quantity = askInt("Ilość: ");
        if (quantity == null) return;

        Offer offer = new Offer(name, description, price, quantity, (Seller) loggedInUser);
        Offer created = offerService.createOffer(offer);
        System.out.println("Oferta utworzona (ID: " + created.getId() + ").");
    }

    private void updateOffer() {
        Integer id = askInt("ID oferty: ");
        if (id == null) return;
        Offer offer = offerRepository.getById(id);
        if (offer == null) { System.out.println("Nie znaleziono oferty."); return; }

        System.out.print("Nowa nazwa [" + offer.getName() + "]: ");
        String name = scanner.nextLine().trim();
        System.out.print("Nowy opis [" + offer.getDescription() + "]: ");
        String description = scanner.nextLine().trim();
        System.out.print("Nowa cena [" + offer.getPrice() + "]: ");
        String priceInput = scanner.nextLine().trim();
        System.out.print("Nowa ilość [" + offer.getQuantity() + "]: ");
        String quantityInput = scanner.nextLine().trim();

        Double price = null;
        Integer quantity = null;
        try {
            if (!priceInput.isEmpty())    price = Double.parseDouble(priceInput);
            if (!quantityInput.isEmpty()) quantity = Integer.parseInt(quantityInput);
        } catch (NumberFormatException e) {
            System.out.println("Niepoprawna cena lub ilość. Zmiany nie zostały zapisane.");
            return;
        }

        if (!name.isEmpty())        offer.setName(name);
        if (!description.isEmpty()) offer.setDescription(description);
        if (price != null)          offer.setPrice(price);
        if (quantity != null)       offer.setQuantity(quantity);

        Offer updated = offerService.updateOffer(offer);
        System.out.println("Oferta zaktualizowana.");
        printOffer(updated);
    }

    private void deleteOffer() {
        Integer id = askInt("ID oferty: ");
        if (id == null) return;
        try {
            offerService.deleteOffer(id);
            System.out.println("Oferta usunięta.");
        } catch (Exception e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    private void reportOffer() {
        Integer id = askInt("ID oferty: ");
        if (id == null) return;
        Offer offer = offerRepository.getById(id);
        if (offer == null) { System.out.println("Nie znaleziono oferty."); return; }

        System.out.print("Powód zgłoszenia: ");
        String reason = scanner.nextLine().trim();

        OfferReport report = offerService.reportOffer(offer, reason, loggedInUser);
        System.out.println("Zgłoszenie wysłane (ID: " + report.getId() + ").");
    }

    private void addToCart() {
        Integer id = askInt("ID oferty: ");
        if (id == null) return;
        Offer offer = offerRepository.getById(id);
        if (offer == null) { System.out.println("Nie znaleziono oferty."); return; }

        cartService.addItem(offer);
        System.out.println("Dodano do koszyka. Liczba produktów: " + cartService.getItems().size());
    }

    private void removeFromCart() {
        ArrayList<Offer> items = cartService.getItems();
        if (items.isEmpty()) { System.out.println("Koszyk jest pusty."); return; }

        System.out.println("Koszyk:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println("  [" + i + "] " + items.get(i).getName());
        }
        Integer index = askInt("Indeks do usunięcia: ");
        if (index == null) return;
        if (index < 0 || index >= items.size()) {
            System.out.println("Niepoprawny indeks.");
            return;
        }
        cartService.removeItem(index);
        System.out.println("Produkt usunięty z koszyka.");
    }

    private void viewCart() {
        ArrayList<Offer> items = cartService.getItems();
        if (items.isEmpty()) {
            System.out.println("Koszyk jest pusty.");
        } else {
            System.out.println("Koszyk (" + items.size() + " produktów):");
            items.forEach(this::printOffer);
        }
    }

    private void clearCart() {
        cartService.clear();
        System.out.println("Koszyk wyczyszczony.");
    }

    private void placeOrder() {
        ArrayList<Offer> items = cartService.getItems();
        if (items.isEmpty()) { System.out.println("Koszyk jest pusty."); return; }

        System.out.print("Kraj: ");
        String country = scanner.nextLine().trim();
        System.out.print("Adres: ");
        String address = scanner.nextLine().trim();
        System.out.print("Miasto: ");
        String city = scanner.nextLine().trim();
        System.out.print("Kod pocztowy: ");
        String postalCode = scanner.nextLine().trim();
        System.out.print("Numer telefonu: ");
        String phoneNumber = scanner.nextLine().trim();
        System.out.print("Województwo: ");
        String voivodeship = scanner.nextLine().trim();
        System.out.print("Metoda płatności (CARD/TRANSFER/CASH): ");
        PaymentMethod paymentMethod;
        try {
            paymentMethod = PaymentMethod.valueOf(scanner.nextLine().trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Niepoprawna metoda płatności.");
            return;
        }

        OrderShippingDetails shippingDetails = new OrderShippingDetails(country, address, city, postalCode, phoneNumber, voivodeship);

        try {
            Order order = orderService.createOrder(new ArrayList<>(items), (Customer) loggedInUser, shippingDetails, paymentMethod);
            System.out.println("Zamówienie złożone (ID: " + order.getId() + ").");
            cartService.clear();
        } catch (Exception e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    private void deleteOrderReview() {
        Integer id = askInt("ID opinii: ");
        if (id == null) return;
        try {
            orderService.deleteOrderReview(id);
            System.out.println("Opinia usunięta.");
        } catch (Exception e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    private void viewMyOrders() {
        ArrayList<Order> orders = orderService.getOrdersForBuyer((Customer) loggedInUser);
        if (orders.isEmpty()) {
            System.out.println("Nie masz żadnych zamówień.");
            return;
        }
        System.out.println("Twoje zamówienia (" + orders.size() + "):");
        for (Order order : orders) {
            System.out.println("  [" + order.getId() + "] status: " + order.getStatus()
                    + " | produktów: " + order.getOffers().size());
        }
    }

    private void completeOrder() {
        Integer id = askInt("ID zamówienia: ");
        if (id == null) return;
        try {
            orderService.completeOrder(id);
            System.out.println("Potwierdzono odbiór zamówienia. Możesz teraz dodać opinię.");
        } catch (Exception e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    private void createOrderReview() {
        Integer orderId = askInt("ID zamówienia: ");
        if (orderId == null) return;
        Order order = orderRepository.getById(orderId);
        if (order == null) { System.out.println("Nie znaleziono zamówienia."); return; }

        Float rating = askFloat("Ocena (1-5): ");
        if (rating == null) return;
        System.out.print("Opis: ");
        String description = scanner.nextLine().trim();

        try {
            OrderReview review = orderService.createOrderReview(order, (Customer) loggedInUser, rating, description);
            System.out.println("Opinia dodana (ID: " + review.getId() + ").");
        } catch (Exception e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    private Integer askInt(String prompt) {
        System.out.print(prompt);
        String raw = scanner.nextLine().trim();
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            System.out.println("Niepoprawna liczba całkowita.");
            return null;
        }
    }

    private Double askDouble(String prompt) {
        System.out.print(prompt);
        String raw = scanner.nextLine().trim();
        try {
            return Double.parseDouble(raw);
        } catch (NumberFormatException e) {
            System.out.println("Niepoprawna liczba.");
            return null;
        }
    }

    private Float askFloat(String prompt) {
        System.out.print(prompt);
        String raw = scanner.nextLine().trim();
        try {
            return Float.parseFloat(raw);
        } catch (NumberFormatException e) {
            System.out.println("Niepoprawna liczba.");
            return null;
        }
    }

    private void printOffer(Offer offer) {
        System.out.println("  [" + offer.getId() + "] " + offer.getName()
                + " - " + offer.getPrice() + " zł"
                + " (ilość: " + offer.getQuantity() + ")"
                + " | " + offer.getDescription());
    }
}