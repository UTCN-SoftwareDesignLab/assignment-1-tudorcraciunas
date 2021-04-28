package com.example.assig2;

import com.example.assig2.item.model.Book;
import com.example.assig2.item.model.DTO.BookDTO;
import com.example.assig2.review.model.dto.ReviewDTO;
import com.example.assig2.user.dto.UserDTO;
import com.example.assig2.user.dto.UserListDTO;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class TestCreationFactory {

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls) {
        return listOf(cls, (Object[]) null);
    }

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls, Object... parameters) {
        int nrElements = new Random().nextInt(10) + 5;
        Supplier<?> supplier;

        if (cls.equals(UserListDTO.class)) {
            supplier = TestCreationFactory::newUserListDTO;
        } else if (cls.equals(Book.class)) {
            supplier = TestCreationFactory::newItem;
        } else if (cls.equals(BookDTO.class)) {
            supplier = TestCreationFactory::newItemDTO;
        } else if (cls.equals(ReviewDTO.class)) {
            supplier = TestCreationFactory::newReviewDTO;
        } else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                (T) finalSupplier.get()
        ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    public static UserDTO newUserListDTO() {
        return UserDTO.builder()
                .id(randomLong())
                .name(randomString())
                .email(randomEmail())
                .build();
    }

    public static Book newItem() {
        return Book.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity(Integer.valueOf(String.valueOf(randomLong())))
                .price(Double.valueOf(String.valueOf(randomLong())))
                .build();
    }

    public static BookDTO newItemDTO() {
        return BookDTO.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity(Integer.valueOf(String.valueOf(randomLong())))
                .price(Float.valueOf(String.valueOf(randomLong())))
                .build();
    }

    private static ReviewDTO newReviewDTO() {
        return ReviewDTO.builder()
                .id(randomLong())
                .text(randomString())
                .reviewer(randomString())
                .build();
    }

    public static String randomEmail() {
        return randomString() + "@" + randomString() + ".com";
    }

    public static byte[] randomBytes() {
        byte[] bytes = new byte[Math.toIntExact(randomLong())];
        new Random().nextBytes(bytes);
        return bytes;
    }

    public static long randomLong() {
        return new Random().nextInt(1999);
    }

    public static Boolean randomBoolean() {
        return new Random().nextBoolean();
    }

    public static int randomBoundedInt(int upperBound) {
        return new Random().nextInt(upperBound);
    }

    public static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
