package dataaccess;

import model.AuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.UUID;

public class MemoryAuthDAOTest {

    private MemoryAuthDAO dao;
    private HashSet<AuthData> expected;

    @BeforeEach
    public void setUp() {
        dao = new MemoryAuthDAO();
        expected = new HashSet<>();
    }

    //test empty
    @Test
    public void testEmptyAuth() {
        HashSet<AuthData> actual = dao.getAuths();
        Assertions.assertEquals(expected, actual);
    }

    //test new auth
    @Test
    public void testCreateAuth() {
        AuthData authData = new AuthData(UUID.randomUUID().toString(), "username");
        expected.add(authData);
        dao.createAuth(authData);
        HashSet<AuthData> actual = dao.getAuths();
        Assertions.assertEquals(expected, actual);
    }

    //test getting auth that exists
    @Test
    public void testGetAuth() {
        AuthData authOne = new AuthData(UUID.randomUUID().toString(), "username");
        AuthData authTwo = new AuthData(UUID.randomUUID().toString(), "anotherusername");
        AuthData authThree = new AuthData(UUID.randomUUID().toString(), "finalusername");

        expected.add(authOne);
        expected.add(authTwo);
        expected.add(authThree);

        dao.createAuth(authOne);
        dao.createAuth(authTwo);
        dao.createAuth(authThree);

        String token = authThree.authToken();

        AuthData actual = dao.getAuth(token);

        Assertions.assertEquals(authThree, actual);
    }

    //test get auth but auth dne
    @Test
    public void testGetAuthDNE() {
        AuthData authOne = new AuthData(UUID.randomUUID().toString(), "username");
        AuthData authTwo = new AuthData(UUID.randomUUID().toString(), "anotherusername");
        AuthData authThree = new AuthData(UUID.randomUUID().toString(), "finalusername");

        expected.add(authOne);
        expected.add(authTwo);
        expected.add(authThree);

        dao.createAuth(authOne);
        dao.createAuth(authTwo);
        dao.createAuth(authThree);

        String dneToken = UUID.randomUUID().toString();

        AuthData actual = dao.getAuth(dneToken);

        Assertions.assertEquals(null, actual);
    }

    //test delete auth
    @Test
    public void testDeleteAuth() {
        AuthData authOne = new AuthData(UUID.randomUUID().toString(), "username");
        AuthData authTwo = new AuthData(UUID.randomUUID().toString(), "anotherusername");
        AuthData authThree = new AuthData(UUID.randomUUID().toString(), "finalusername");

        expected.add(authOne);
        expected.add(authThree);

        dao.createAuth(authOne);
        dao.createAuth(authTwo);
        dao.createAuth(authThree);

        dao.deleteAuth(authTwo);

        HashSet<AuthData> actual = dao.getAuths();
        Assertions.assertEquals(expected, actual);
    }

    //test delete auth but there are none
    @Test
    public void testDeleteAuthEmpty() {
        AuthData authData = new AuthData(UUID.randomUUID().toString(), "username");

        dao.deleteAuth(authData);

        HashSet<AuthData> actual = dao.getAuths();
        Assertions.assertEquals(expected, actual);
    }

    //test delete auth doesn't exist
    public void testDeleteAuthDNE() {
        AuthData authOne = new AuthData(UUID.randomUUID().toString(), "username");
        AuthData authTwo = new AuthData(UUID.randomUUID().toString(), "anotherusername");
        AuthData authThree = new AuthData(UUID.randomUUID().toString(), "finalusername");

        expected.add(authOne);
        expected.add(authTwo);
        expected.add(authThree);

        dao.createAuth(authOne);
        dao.createAuth(authTwo);
        dao.createAuth(authThree);

        AuthData dneAuth = new AuthData(UUID.randomUUID().toString(), "User DNE");

        dao.deleteAuth(dneAuth);

        HashSet<AuthData> actual = dao.getAuths();
        Assertions.assertEquals(expected, actual);
    }

    //test delete all auths
    @Test
    public void testDeleteAllAuth() {
        AuthData authOne = new AuthData(UUID.randomUUID().toString(), "username");
        AuthData authTwo = new AuthData(UUID.randomUUID().toString(), "anotherusername");
        AuthData authThree = new AuthData(UUID.randomUUID().toString(), "finalusername");

        dao.createAuth(authOne);
        dao.createAuth(authTwo);
        dao.createAuth(authThree);

        dao.deleteAllAuths();

        HashSet<AuthData> actual = dao.getAuths();
        Assertions.assertEquals(expected, actual);
    }

    //test delete all auths but no auths exist
    @Test
    public void testDeleteAllAuthDNE() {
        dao.deleteAllAuths();

        HashSet<AuthData> actual = dao.getAuths();
        Assertions.assertEquals(expected, actual);
    }
}
