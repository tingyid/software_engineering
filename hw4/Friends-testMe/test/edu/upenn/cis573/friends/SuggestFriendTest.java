package edu.upenn.cis573.friends;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test suite for FriendFinder.suggestFriend
 * method. Tests each scenario listed by the
 * specification, utilizing dependency
 * injection to avoid connecting to the database 
 */
public class SuggestFriendTest {
	
	@Test
	public void testPersonHasNoFriends() {
		
		FriendFinder friendFinder = new FriendFinder() {
			protected DataSource createFriendsDataSource() {
				return new FriendsDataSource() {
					public List<String> get(String arg) {
						return null;
					}
				};
			}
		};
		
		String msg = "Should return null for person with no friends";
		String expected = null;
		String actual = friendFinder.suggestFriend("Platypus");
		Assert.assertEquals(msg, expected, actual);
	}
	
	@Test
	public void testPersonHasTwoFriendsButTheyAreOnlyFriendsWithPerson() {
		
		FriendFinder friendFinder = new FriendFinder() {
			protected DataSource createFriendsDataSource() {
				return new FriendsDataSource() {
					public List<String> get(String arg) {
						List<String> friendList = new ArrayList<String>();
						if (arg.equals("Platypus")) {
							friendList.add("Aardvark");
							friendList.add("Hippo");
						} else if (arg.equals("Aardvark")) {
							friendList.add("Platypus");
						} else if (arg.equals("Hippo")) {
							friendList.add("Platypus");
						}
						return friendList;
					}
				};
			}
		};
		
		String msg = "Should return null for person whose friends have no friends other than them";
		String expected = null;
		String actual = friendFinder.suggestFriend("Platypus");
		Assert.assertEquals(msg, expected, actual);
	}
	
	@Test
	public void testPersonHasTwoFriendsButTheyAreOnlyFriendsWithEachOtherAndPerson() {
		
		FriendFinder friendFinder = new FriendFinder() {
			protected DataSource createFriendsDataSource() {
				return new FriendsDataSource() {
					public List<String> get(String arg) {
						List<String> friendList = new ArrayList<String>();
						if (arg.equals("Platypus")) {
							friendList.add("Aardvark");
							friendList.add("Hippo");
						} else if (arg.equals("Aardvark")) {
							friendList.add("Platypus");
							friendList.add("Hippo");
						} else if (arg.equals("Hippo")) {
							friendList.add("Platypus");
							friendList.add("Aardvark");
						}
						return friendList;
					}
				};
			}
		};
		
		String msg = "Should return null for person whose friends are only friends with each other";
		String expected = null;
		String actual = friendFinder.suggestFriend("Platypus");
		Assert.assertEquals(msg, expected, actual);
	}
	
	@Test
	public void testPersonHasOneFriendAndTheyHaveOneOtherFriend() {
		
		FriendFinder friendFinder = new FriendFinder() {
			protected DataSource createFriendsDataSource() {
				return new FriendsDataSource() {
					public List<String> get(String arg) {
						List<String> friendList = new ArrayList<String>();
						if (arg.equals("Platypus")) {
							friendList.add("Aardvark");
						} else if (arg.equals("Aardvark")) {
							friendList.add("Platypus");
							friendList.add("Hippo");
						}
						return friendList;
					}
				};
			}
		};
				
		String msg = "Should return the friends other friend for person";
		String expected = "Hippo";
		String actual = friendFinder.suggestFriend("Platypus");
		Assert.assertEquals(msg, expected, actual);
	}
	
	@Test
	public void testPersonHasOneFriendAndTheyHaveTwoOtherFriends() {
		
		FriendFinder friendFinder = new FriendFinder() {
			protected DataSource createFriendsDataSource() {
				return new FriendsDataSource() {
					public List<String> get(String arg) {
						List<String> friendList = new ArrayList<String>();
						if (arg.equals("Platypus")) {
							friendList.add("Aardvark");
						} else if (arg.equals("Aardvark")) {
							friendList.add("Platypus");
							friendList.add("Hippo");
							friendList.add("Ocelot");
						}
						return friendList;
					}
				};
			}
		};
				
		String msg = "Should return one of the friends other friends for person";
		List<String> expected = new ArrayList<String>();
		expected.add("Hippo");
		expected.add("Ocelot");
		String actual = friendFinder.suggestFriend("Platypus");
		Assert.assertTrue(msg, expected.contains(actual)); //check that one of the friends is returned
	}
	
	@Test
	public void testPersonHasTwoFriendsAndTheyHaveOneOtherFriendEachAndMutual() {
		
		FriendFinder friendFinder = new FriendFinder() {
			protected DataSource createFriendsDataSource() {
				return new FriendsDataSource() {
					public List<String> get(String arg) {
						List<String> friendList = new ArrayList<String>();
						if (arg.equals("Platypus")) {
							friendList.add("Aardvark");
							friendList.add("Hippo");
						} else if (arg.equals("Aardvark")) {
							friendList.add("Platypus");
							friendList.add("Ocelot");
						} else if (arg.equals("Hippo")) {
							friendList.add("Platypus");
							friendList.add("Ocelot");
						}
						return friendList;
					}
				};
			}
		};
				
		String msg = "Should return the mutual friend of both friends for person";
		String expected = "Ocelot";
		String actual = friendFinder.suggestFriend("Platypus");
		Assert.assertEquals(msg, expected, actual);
	}
	
	@Test
	public void testPersonHasTwoFriendsAndTheyHaveTwoOtherFriendsEachButNoMutual() {
		
		FriendFinder friendFinder = new FriendFinder() {
			protected DataSource createFriendsDataSource() {
				return new FriendsDataSource() {
					public List<String> get(String arg) {
						List<String> friendList = new ArrayList<String>();
						if (arg.equals("Platypus")) {
							friendList.add("Aardvark");
							friendList.add("Hippo");
						} else if (arg.equals("Aardvark")) {
							friendList.add("Platypus");
							friendList.add("Jaguar");
							friendList.add("Ocelot");
						} else if (arg.equals("Hippo")) {
							friendList.add("Platypus");
							friendList.add("Tiger");
							friendList.add("Gorilla");
						}
						return friendList;
					}
				};
			}
		};
				
		String msg = "Should return one of the friends other friends for person";
		List<String> expected = new ArrayList<String>();
		expected.add("Jaguar");
		expected.add("Ocelot");
		expected.add("Tiger");
		expected.add("Gorilla");
		String actual = friendFinder.suggestFriend("Platypus");
		Assert.assertTrue(msg, expected.contains(actual)); //check that one of the friends is returned
	}
	
	@Test
	public void testPersonHasThreeFriendsAndTheyHaveTwoOtherFriendsEachOneMutualToAllOneMutualToAllButOne() {
		
		FriendFinder friendFinder = new FriendFinder() {
			protected DataSource createFriendsDataSource() {
				return new FriendsDataSource() {
					public List<String> get(String arg) {
						List<String> friendList = new ArrayList<String>();
						if (arg.equals("Platypus")) {
							friendList.add("Aardvark");
							friendList.add("Hippo");
							friendList.add("Ocelot");
						} else if (arg.equals("Aardvark")) {
							friendList.add("Platypus");
							friendList.add("Jaguar");
							friendList.add("Gorilla");
						} else if (arg.equals("Hippo")) {
							friendList.add("Platypus");
							friendList.add("Jaguar");
							friendList.add("Gorilla");
						} else if (arg.equals("Ocelot")) {
							friendList.add("Platypus");
							friendList.add("Jaguar");
							friendList.add("Tiger");
						}
						return friendList;
					}
				};
			}
		};
				
		String msg = "Should return the mutual friend of all three friends for person";
		String expected = "Jaguar";
		String actual = friendFinder.suggestFriend("Platypus");
		Assert.assertEquals(msg, expected, actual);
	}
	
	@Test
	public void testPersonHasThreeFriendsAndTheyHaveThreeOtherFriendsEachTwoMutualToAllRestNotInCommon() {
		
		FriendFinder friendFinder = new FriendFinder() {
			protected DataSource createFriendsDataSource() {
				return new FriendsDataSource() {
					public List<String> get(String arg) {
						List<String> friendList = new ArrayList<String>();
						if (arg.equals("Platypus")) {
							friendList.add("Aardvark");
							friendList.add("Hippo");
							friendList.add("Ocelot");
						} else if (arg.equals("Aardvark")) {
							friendList.add("Platypus");
							friendList.add("Jaguar");
							friendList.add("Gorilla");
							friendList.add("Penguin");
						} else if (arg.equals("Hippo")) {
							friendList.add("Platypus");
							friendList.add("Jaguar");
							friendList.add("Gorilla");
							friendList.add("Ostrich");
						} else if (arg.equals("Ocelot")) {
							friendList.add("Platypus");
							friendList.add("Jaguar");
							friendList.add("Gorilla");
							friendList.add("Tiger");
						}
						return friendList;
					}
				};
			}
		};
				
		String msg = "Should return one of the mutual friends for all three friends of person";
		List<String> expected = new ArrayList<String>();
		expected.add("Jaguar");
		expected.add("Gorilla");
		String actual = friendFinder.suggestFriend("Platypus");
		Assert.assertTrue(msg, expected.contains(actual)); //check that one of the friends is returned
	}

}
