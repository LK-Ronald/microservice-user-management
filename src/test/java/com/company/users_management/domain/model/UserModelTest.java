package com.company.users_management.domain.model;

import com.company.users_management.domain.enums.UserRol;
import com.company.users_management.domain.enums.UserStatus;
import com.company.users_management.domain.valueobject.UserEmail;
import com.company.users_management.domain.valueobject.UserId;
import com.company.users_management.domain.valueobject.UserName;
import com.company.users_management.domain.valueobject.UserPassword;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class UserModelTest {

    private static final UserId ID = new UserId("user-123");
    private static final UserName NAME = new UserName("Juan Perez");
    private static final UserEmail EMAIL = new UserEmail("juan.perez@example.com");
    private static final String PLAIN_PASSWORD = "Test-only-123!";
    private static final UserPassword PASSWORD = testPassword(PLAIN_PASSWORD);

    @ParameterizedTest
    @EnumSource(UserRol.class)
    void createsPendingUserForEveryRole(UserRol role) {
        UserModel user = UserModel.create(ID, NAME, EMAIL, PASSWORD, role);

        assertUserFields(user, ID, NAME, EMAIL, PASSWORD, role, UserStatus.PENDING);
    }

    @ParameterizedTest
    @MethodSource("roleAndStatusCombinations")
    void constructorPreservesEveryRoleAndStatus(UserRol role, UserStatus status) {
        UserModel user = new UserModel(ID, NAME, EMAIL, PASSWORD, role, status);

        assertUserFields(user, ID, NAME, EMAIL, PASSWORD, role, status);
    }

    @Test
    void factoryAndConstructorProduceEqualPendingUsers() {
        UserModel created = UserModel.create(ID, NAME, EMAIL, PASSWORD, UserRol.USER);
        UserModel constructed = new UserModel(
                new UserId(" user-123 "), new UserName(" Juan Perez "),
                new UserEmail(" JUAN.PEREZ@EXAMPLE.COM "), PASSWORD, UserRol.USER, UserStatus.PENDING);

        assertAll(
                () -> assertEquals(created, constructed),
                () -> assertEquals(constructed, created),
                () -> assertEquals(created.hashCode(), constructed.hashCode()),
                () -> assertNotEquals(created, null),
                () -> assertNotEquals(created, ID));
    }

    @ParameterizedTest(name = "different {0}")
    @MethodSource("usersWithOneDifferentField")
    void equalityIncludesEveryField(String field, UserModel different) {
        UserModel original = UserModel.create(ID, NAME, EMAIL, PASSWORD, UserRol.USER);

        assertNotEquals(original, different, field);
    }

    @Test
    void stringRepresentationDoesNotExposePasswordOrHash() {
        UserModel user = UserModel.create(ID, NAME, EMAIL, PASSWORD, UserRol.USER);
        String representation = user.toString();

        assertFalse(representation.contains(PLAIN_PASSWORD));
        assertFalse(representation.contains(PASSWORD.value()));
    }

    @ParameterizedTest(name = "missing {0}")
    @MethodSource("usersWithAbsentFields")
    void factoryPreservesAbsentFieldsAndDefaultsStatus(
            String fields, UserId id, UserName name, UserEmail email, UserPassword password, UserRol role
    ) {
        UserModel user = UserModel.create(id, name, email, password, role);

        assertUserFields(user, id, name, email, password, role, UserStatus.PENDING);
    }

    @ParameterizedTest(name = "missing {0}")
    @MethodSource("usersWithAbsentFields")
    void constructorPreservesAbsentFieldsAndExplicitStatus(
            String fields, UserId id, UserName name, UserEmail email, UserPassword password, UserRol role
    ) {
        UserModel user = new UserModel(id, name, email, password, role, UserStatus.ACTIVE);

        assertUserFields(user, id, name, email, password, role, UserStatus.ACTIVE);
    }

    @Test
    void constructorPreservesAbsentStatusWithAllOtherFieldsPresent() {
        UserModel user = new UserModel(ID, NAME, EMAIL, PASSWORD, UserRol.USER, null);

        assertUserFields(user, ID, NAME, EMAIL, PASSWORD, UserRol.USER, null);
    }

    @Test
    void constructorPreservesAllFieldsWhenAbsent() {
        UserModel user = new UserModel(null, null, null, null, null, null);

        assertUserFields(user, null, null, null, null, null, null);
    }

    private static Stream<Arguments> usersWithAbsentFields() {
        return Stream.of(
                Arguments.of("id", null, NAME, EMAIL, PASSWORD, UserRol.USER),
                Arguments.of("name", ID, null, EMAIL, PASSWORD, UserRol.USER),
                Arguments.of("email", ID, NAME, null, PASSWORD, UserRol.USER),
                Arguments.of("password", ID, NAME, EMAIL, null, UserRol.USER),
                Arguments.of("role", ID, NAME, EMAIL, PASSWORD, null),
                Arguments.of("all fields", null, null, null, null, null));
    }

    private static Stream<Arguments> roleAndStatusCombinations() {
        return Arrays.stream(UserRol.values()).flatMap(role ->
                Arrays.stream(UserStatus.values()).map(status -> Arguments.of(role, status)));
    }

    private static Stream<Arguments> usersWithOneDifferentField() {
        return Stream.of(
                Arguments.of("id", UserModel.create(new UserId("user-456"), NAME, EMAIL, PASSWORD, UserRol.USER)),
                Arguments.of("name", UserModel.create(ID, new UserName("Maria Perez"), EMAIL, PASSWORD, UserRol.USER)),
                Arguments.of("email", UserModel.create(ID, NAME, new UserEmail("other@example.com"), PASSWORD, UserRol.USER)),
                Arguments.of("password", UserModel.create(ID, NAME, EMAIL, testPassword("Other-test-123!"), UserRol.USER)),
                Arguments.of("role", UserModel.create(ID, NAME, EMAIL, PASSWORD, UserRol.ADMIN)),
                Arguments.of("status", new UserModel(ID, NAME, EMAIL, PASSWORD, UserRol.USER, UserStatus.ACTIVE)));
    }

    private static void assertUserFields(
            UserModel user, UserId id, UserName name, UserEmail email,
            UserPassword password, UserRol role, UserStatus status
    ) {
        assertAll(
                () -> assertSame(id, user.id()),
                () -> assertSame(name, user.name()),
                () -> assertSame(email, user.email()),
                () -> assertSame(password, user.password()),
                () -> assertSame(role, user.rol()),
                () -> assertSame(status, user.status()));
    }

    private static UserPassword testPassword(String plaintext) {
        return UserPassword.fromHash(BCrypt.hashpw(plaintext, BCrypt.gensalt(4)));
    }
}
