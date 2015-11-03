package edu.upenn.cis573.friends;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FindClassmatesTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testPersonTakingNoClasses() {
		
		FriendFinder friendFinder = new FriendFinder() {
			protected DataSource createClassesDataSource() {
				return new ClassesDataSource() {
					public List<String> get(String arg) {
						return null;
					}
				};
			}
		};
		
		String msg = "Should return null for person with no classes";
		List<String> expected = null;
		List<String> actual = friendFinder.findClassmates("Platypus");
		Assert.assertEquals(msg, expected, actual);
	}
	
	@Test
	public void testPersonTakingOneClassAndNoStudentsInThem() {
		
		FriendFinder friendFinder = new FriendFinder() {
			protected DataSource createClassesDataSource() {
				return new ClassesDataSource() {
					public List<String> get(String arg) {
						List<String> classes = new ArrayList<String>();
						classes.add("CIS573");
						return classes;
					}
				};
			}
			protected DataSource createStudentsDataSource() {
				return new StudentsDataSource() {
					public List<String> get(String arg) {
						return new ArrayList<String>();
					}
				};
			}
		};
		
		String msg = "Should return null for person with one class with no other students";
		List<String> expected = null;
		List<String> actual = friendFinder.findClassmates("Platypus");
		Assert.assertEquals(msg, expected, actual);
	}
	
	@Test
	public void testPersonTakingTwoClassesAndOneStudentTakingPartialSubset() {
		
		FriendFinder friendFinder = new FriendFinder() {
			protected DataSource createClassesDataSource() {
				return new ClassesDataSource() {
					public List<String> get(String arg) {
						List<String> classes = new ArrayList<String>();
						if (arg.equals("Platypus")) {
							classes.add("CIS573");
							classes.add("CIS550");
						} else if (arg.equals("Aardvark")) {
							classes.add("CIS573");
							classes.add("CIS555");
						}
						return classes;
					}
				};
			}
			protected DataSource createStudentsDataSource() {
				return new StudentsDataSource() {
					public List<String> get(String arg) {
						List<String> students = new ArrayList<String>();
						if (arg.equals("CIS573")) {
							students.add("Platypus");
							students.add("Aardvark");
						}
						return students;
					}
				};
			}
		};
		
		String msg = "Should return null for person with two classes and no full overlap with other students";
		List<String> expected = null;
		List<String> actual = friendFinder.findClassmates("Platypus");
		Assert.assertEquals(msg, expected, actual);
	}
	
	@Test
	public void testPersonTakingTwoClassesAndOneStudentTakingSameAndAnotherTakingPartialSubset() {
		
		FriendFinder friendFinder = new FriendFinder() {
			protected DataSource createClassesDataSource() {
				return new ClassesDataSource() {
					public List<String> get(String arg) {
						List<String> classes = new ArrayList<String>();
						if (arg.equals("Platypus")) {
							classes.add("CIS573");
							classes.add("CIS550");
						} else if (arg.equals("Aardvark")) {
							classes.add("CIS573");
							classes.add("CIS550");
						} else if (arg.equals("Ocelot")) {
							classes.add("CIS573");
							classes.add("CIS555");
						}
						return classes;
					}
				};
			}
			protected DataSource createStudentsDataSource() {
				return new StudentsDataSource() {
					public List<String> get(String arg) {
						List<String> students = new ArrayList<String>();
						if (arg.equals("CIS573")) {
							students.add("Platypus");
							students.add("Aardvark");
						} else if (arg.equals("CIS550")) {
							students.add("Platypus");
							students.add("Aardvark");
						} else if (arg.equals("CIS555")) {
							students.add("Ocelot");
						}
						return students;
					}
				};
			}
		};
		
		String msg = "Should return the student with full class overlap with person";
		String expected = "Aardvark";
		List<String> actual = friendFinder.findClassmates("Platypus");
		Assert.assertNotNull(msg, actual);
		Assert.assertEquals(msg, 1, actual.size());
		Assert.assertEquals(msg, expected, actual.get(0));
	}
	
	@Test
	public void testPersonTakingTwoClassesAndTwoStudentsTakingSameAndAnotherTakingPartialSubset() {
		
		FriendFinder friendFinder = new FriendFinder() {
			protected DataSource createClassesDataSource() {
				return new ClassesDataSource() {
					public List<String> get(String arg) {
						List<String> classes = new ArrayList<String>();
						if (arg.equals("Platypus")) {
							classes.add("CIS573");
							classes.add("CIS550");
						} else if (arg.equals("Aardvark")) {
							classes.add("CIS573");
							classes.add("CIS550");
						} else if (arg.equals("Hippo")) {
							classes.add("CIS573");
							classes.add("CIS550");
						} else if (arg.equals("Ocelot")) {
							classes.add("CIS573");
							classes.add("CIS555");
						}
						return classes;
					}
				};
			}
			protected DataSource createStudentsDataSource() {
				return new StudentsDataSource() {
					public List<String> get(String arg) {
						List<String> students = new ArrayList<String>();
						if (arg.equals("CIS573")) {
							students.add("Platypus");
							students.add("Aardvark");
							students.add("Hippo");
						} else if (arg.equals("CIS550")) {
							students.add("Platypus");
							students.add("Aardvark");
							students.add("Hippo");
						} else if (arg.equals("CIS555")) {
							students.add("Ocelot");
						}
						return students;
					}
				};
			}
		};
		
		String msg = "Should return the students with full class overlap with person";
		List<String> actual = friendFinder.findClassmates("Platypus");
		Assert.assertNotNull(msg, actual);
		Assert.assertEquals(msg, 2, actual.size());
		Assert.assertTrue(msg, actual.contains("Aardvark"));
		Assert.assertTrue(msg, actual.contains("Hippo"));
	}
	
	@Test
	public void testPersonTakingTwoClassesAndTwoStudentsTakingSupersetAndAnotherTakingPartialSubset() {
		
		FriendFinder friendFinder = new FriendFinder() {
			protected DataSource createClassesDataSource() {
				return new ClassesDataSource() {
					public List<String> get(String arg) {
						List<String> classes = new ArrayList<String>();
						if (arg.equals("Platypus")) {
							classes.add("CIS573");
							classes.add("CIS550");
						} else if (arg.equals("Aardvark")) {
							classes.add("CIS573");
							classes.add("CIS550");
							classes.add("CIS553");
						} else if (arg.equals("Hippo")) {
							classes.add("CIS573");
							classes.add("CIS550");
							classes.add("CIS555");
						} else if (arg.equals("Ocelot")) {
							classes.add("CIS573");
							classes.add("CIS555");
						}
						return classes;
					}
				};
			}
			protected DataSource createStudentsDataSource() {
				return new StudentsDataSource() {
					public List<String> get(String arg) {
						List<String> students = new ArrayList<String>();
						if (arg.equals("CIS573")) {
							students.add("Platypus");
							students.add("Aardvark");
							students.add("Hippo");
						} else if (arg.equals("CIS550")) {
							students.add("Platypus");
							students.add("Aardvark");
							students.add("Hippo");
						} else if (arg.equals("CIS553")) {
							students.add("Aardvark");
						} else if (arg.equals("CIS555")) {
							students.add("Ocelot");
							students.add("Hippo");
						}
						return students;
					}
				};
			}
		};
		
		String msg = "Should return the students with full class overlap with person";
		List<String> actual = friendFinder.findClassmates("Platypus");
		Assert.assertNotNull(msg, actual);
		Assert.assertEquals(msg, 2, actual.size());
		Assert.assertTrue(msg, actual.contains("Aardvark"));
		Assert.assertTrue(msg, actual.contains("Hippo"));
	}
	
	@Test
	public void testPersonTakingTwoClassesAndTwoStudentsTakingSameAndTwoStudentsTakingSupersetAndAnotherTakingPartialSubset() {
		
		FriendFinder friendFinder = new FriendFinder() {
			protected DataSource createClassesDataSource() {
				return new ClassesDataSource() {
					public List<String> get(String arg) {
						List<String> classes = new ArrayList<String>();
						if (arg.equals("Platypus")) {
							classes.add("CIS573");
							classes.add("CIS550");
						} else if (arg.equals("Aardvark")) {
							classes.add("CIS573");
							classes.add("CIS550");
							classes.add("CIS553");
						} else if (arg.equals("Hippo")) {
							classes.add("CIS573");
							classes.add("CIS550");
							classes.add("CIS555");
						} else if (arg.equals("Jaguar")) {
							classes.add("CIS573");
							classes.add("CIS550");
						} else if (arg.equals("Gorilla")) {
							classes.add("CIS573");
							classes.add("CIS550");
						} else if (arg.equals("Ocelot")) {
							classes.add("CIS573");
							classes.add("CIS555");
						}
						return classes;
					}
				};
			}
			protected DataSource createStudentsDataSource() {
				return new StudentsDataSource() {
					public List<String> get(String arg) {
						List<String> students = new ArrayList<String>();
						if (arg.equals("CIS573")) {
							students.add("Platypus");
							students.add("Aardvark");
							students.add("Hippo");
							students.add("Jaguar");
							students.add("Gorilla");
						} else if (arg.equals("CIS550")) {
							students.add("Platypus");
							students.add("Aardvark");
							students.add("Hippo");
							students.add("Jaguar");
							students.add("Gorilla");
						} else if (arg.equals("CIS553")) {
							students.add("Aardvark");
						} else if (arg.equals("CIS555")) {
							students.add("Ocelot");
							students.add("Hippo");
						}
						return students;
					}
				};
			}
		};
		
		String msg = "Should return the students with full class overlap with person";
		List<String> actual = friendFinder.findClassmates("Platypus");
		Assert.assertNotNull(msg, actual);
		Assert.assertEquals(msg, 4, actual.size());
		Assert.assertTrue(msg, actual.contains("Aardvark"));
		Assert.assertTrue(msg, actual.contains("Hippo"));
		Assert.assertTrue(msg, actual.contains("Gorilla"));
		Assert.assertTrue(msg, actual.contains("Jaguar"));
	}
	

}
