package gymman.auth;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCanVerifyPassword() {
		User user = new User("pippo", "ROLE_1", "plutopluto");

		assertThat(user.verifyPassword("plutopluto"), is(true));
		assertThat(user.verifyPassword("paperino"), is(false));
	}

	@Test
	public void testCanGetRole() {
		User user = new User("pippo", "ROLE_1", "plutopluto");

		assertThat(user.getRole(), is("ROLE_1"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testErrorOnInvalidPassword() {
		new User("pippo", "ROLE_1", "abc"); // password is not long enough
	}
}
