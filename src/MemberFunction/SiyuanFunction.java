package MemberFunction;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import Helper.Global;
import cs555_tm5_project.FamilyInfo;
import cs555_tm5_project.IndividualInfo;

public class SiyuanFunction {

	public static void wrongMarrigeSex(HashMap individualInfo,
			HashMap familyInfo) {
		System.out.println("-----------------------------");
		System.out.println(" Output for US07 : Wrong marriage sex");
		System.out.println("-----------------------------\n");

		if (familyInfo != null && !familyInfo.isEmpty()) {
			Object[] famKey = familyInfo.keySet().toArray();
			Arrays.sort(famKey);

			for (Object fam : famKey) {
				FamilyInfo famInfo = (FamilyInfo) familyInfo.get(fam);

				if (famInfo.getHusband() != 0 && famInfo.getWife() != 0) {
					IndividualInfo husbandInfo = (IndividualInfo) individualInfo
							.get(famInfo.getHusband());
					IndividualInfo wifeInfo = (IndividualInfo) individualInfo
							.get(famInfo.getWife());

					if (!husbandInfo.getSex().trim().equalsIgnoreCase("M")
							|| !wifeInfo.getSex().trim().equalsIgnoreCase("F")) {
						String husId = Global.rebuildIdentifier(
								Integer.toString(famInfo.getHusband()), 'I');
						String wifeId = Global.rebuildIdentifier(
								Integer.toString(famInfo.getWife()), 'I');

						System.out
								.println("Wrong marriage sex between husband: "
										+ husId + husbandInfo.getName()
										+ " and wife:" + wifeId
										+ wifeInfo.getName());
					}
				}
			}

		}
		System.out.println("-----------------------------");
		System.out.println(" End of Output for US07 ");
		System.out.println("-----------------------------\n");

	}

	public static void wrongChildrenBirthday(HashMap individualInfo,
			HashMap familyInfo) {
		System.out.println("-----------------------------");
		System.out.println("Output for US08: child's birthday before parents ");
		System.out.println("-----------------------------\n");

		if (familyInfo != null && !familyInfo.isEmpty()) {
			Object[] famKey = familyInfo.keySet().toArray();
			Arrays.sort(famKey);

			for (Object fam : famKey) {
				FamilyInfo famInfo = (FamilyInfo) familyInfo.get(fam);

				if (famInfo.getHusband() != 0 && famInfo.getWife() != 0) {

					IndividualInfo dadInfo = (IndividualInfo) individualInfo
							.get(famInfo.getHusband());

					IndividualInfo momInfo = (IndividualInfo) individualInfo
							.get(famInfo.getWife());
					if (dadInfo.getBirthDate() != null
							&& momInfo.getBirthDate() != null) {
						Date dadBirth = (Date) dadInfo.getBirthDate();
						Date momBirth = (Date) momInfo.getBirthDate();

						if (famInfo.getChildren() != null) {

							ArrayList<Integer> children = famInfo.getChildren();

							for (Integer child : children) {

								IndividualInfo childInfo = (IndividualInfo) individualInfo
										.get(child);
								if (childInfo.getBirthDate() != null) {
									Date childBirth = childInfo.getBirthDate();

									if (!(childBirth.after(momBirth) && childBirth
											.after(dadBirth))) {
										String childId = Global
												.rebuildIdentifier(
														Integer.toString(child),
														'I');

										System.out
												.println("Wrong birthday for child :"
														+ childId
														+ childInfo.getName());
									}
								}
							}
						}
					}
				}
			}
		}

		System.out.println("-----------------------------");
		System.out.println("End of Output for US08 ");
		System.out.println("-----------------------------\n");
	}

	public static void childrenAmountExceed(HashMap individualInfo,
			HashMap familyInfo, int limit) {
		System.out.println("-----------------------------");
		System.out
				.println("Output for US15: List the family has more than limit amount of children ");
		System.out.println("-----------------------------\n");
		String result = "";

		if (familyInfo != null && !familyInfo.isEmpty()) {
			Object[] famKey = familyInfo.keySet().toArray();
			Arrays.sort(famKey);
			int famID = 0;
			for (Object fam : famKey) {
				famID++;
				FamilyInfo famInfo = (FamilyInfo) familyInfo.get(fam);

				if (famInfo.getChildren() != null) {
					ArrayList<Integer> famChildren = famInfo.getChildren();

					if (famChildren.size() > limit) {
						if (famInfo.getHusband() != 0 && famInfo.getWife() != 0) {
							IndividualInfo husband = (IndividualInfo) individualInfo
									.get(famInfo.getHusband());
							IndividualInfo wife = (IndividualInfo) individualInfo
									.get(famInfo.getWife());

							String theFamID = Global.rebuildIdentifier(
									Integer.toString(famID), 'F');
							result = "Family " + theFamID + " with husband @I"
									+ famInfo.getHusband() + " ("
									+ husband.getName() + ")" + " and wife @I"
									+ famInfo.getWife() + " (" + wife.getName()
									+ ")" + " has more than " + limit
									+ " children.";
							System.out.println(result);
						}
					}
				}
			}
		}

		System.out.println("-----------------------------");
		System.out.println("End of Output for US15 ");
		System.out.println("-----------------------------\n");

	}

	public static void genderRatio(HashMap individualInfo, HashMap familyInfo) {
		System.out.println("-----------------------------");
		System.out
				.println("Output for US18: List the gender ratio for each family ");
		System.out.println("-----------------------------\n");
		String result = "";

		if (familyInfo != null && !familyInfo.isEmpty()) {
			Object[] famKey = familyInfo.keySet().toArray();
			Arrays.sort(famKey);
			int famID = 0;
			for (Object fam : famKey) {
				famID++;
				FamilyInfo famInfo = (FamilyInfo) familyInfo.get(fam);

				int maleThisFam = 0;
				int femaleThisFam = 0;

				if (famInfo.getHusband() != 0)
					maleThisFam++;
				if (famInfo.getWife() != 0)
					femaleThisFam++;
				if (famInfo.getChildren() != null) {
					ArrayList<Integer> children = famInfo.getChildren();
					for (Integer child : children) {
						IndividualInfo childInfo = (IndividualInfo) individualInfo
								.get(child);
						if (childInfo.getSex().equals("M"))
							maleThisFam++;
						if (childInfo.getSex().equals("F"))
							femaleThisFam++;
					}
				}
				result = "Family @F" + famID + " has male members #"
						+ maleThisFam + " and female members #" + femaleThisFam;

				System.out.println(result);
			}
		}

		System.out.println("-----------------------------");
		System.out.println("End of Output for US18 ");
		System.out.println("-----------------------------\n");
	}

	@SuppressWarnings("deprecation")
	public static void youngMom(HashMap<Integer, FamilyInfo> familyInfoObjMap,
			HashMap<Integer, IndividualInfo> individualInfoObjMap) {
		Global.printTitle("US 25: Show all moms whoes age are too young");

		if (familyInfoObjMap != null && !familyInfoObjMap.isEmpty()) {
			Object[] famKey = familyInfoObjMap.keySet().toArray();
			Arrays.sort(famKey);

			for (Object retval : famKey) {
				FamilyInfo famInfo = (FamilyInfo) familyInfoObjMap.get(retval);
				if (famInfo.getWife() != 0 && famInfo.getChildren() != null) {
					IndividualInfo mom = individualInfoObjMap.get(famInfo
							.getWife());
					if (mom.getBirthDate() == null) {
						// System.out.println("Error: No birthday for mom"+
						// mom.getName());
						continue;
					}
					Date momBirth = mom.getBirthDate();
					Date momBirthTmp = (Date) momBirth.clone();
					momBirthTmp.setYear((momBirthTmp.getYear() + 1970));

					ArrayList<Integer> childrenInfo = famInfo.getChildren();
					for (Object ret : childrenInfo) {
						IndividualInfo child = individualInfoObjMap.get(ret);
						if (child.getBirthDate() == null) {
							// System.out.println("Error: No birthday for child: "+
							// child.getName());
							continue;
						}
						Date childBirth = child.getBirthDate();
						Date childBirthTmp = (Date) childBirth.clone();
						childBirthTmp.setYear((childBirthTmp.getYear() + 1970));
						long diff = childBirthTmp.getTime()
								/ (1000 * 60 * 60 * 24) - momBirthTmp.getTime()
								/ (1000 * 60 * 60 * 24);
						long diffYear = diff / 365;
						if (diffYear < 10) {
							System.out.println("Age difference between mom "
									+ mom.getName() + " and child "
									+ child.getName() + " is less than 10.");
						}
					}
				}
			}
		}
	}

	public static void oldMom(HashMap<Integer, FamilyInfo> familyInfoObjMap,
			HashMap<Integer, IndividualInfo> individualInfoObjMap) {
		Global.printTitle("US 26: Show all moms whoes age are too old");
		if (familyInfoObjMap != null && !familyInfoObjMap.isEmpty()) {
			Object[] famKey = familyInfoObjMap.keySet().toArray();
			Arrays.sort(famKey);

			for (Object retval : famKey) {
				FamilyInfo famInfo = (FamilyInfo) familyInfoObjMap.get(retval);
				if (famInfo.getWife() != 0 && famInfo.getChildren() != null) {
					IndividualInfo mom = individualInfoObjMap.get(famInfo
							.getWife());

					if (mom.getBirthDate() == null) {
						// System.out.println("Error: No birthday for mom"+
						// mom.getName());
						continue;
					}
					Date momBirth = mom.getBirthDate();
					Date momBirthTmp = (Date) momBirth.clone();
					momBirthTmp.setYear((momBirthTmp.getYear() + 1970));

					ArrayList<Integer> childrenInfo = famInfo.getChildren();
					for (Object ret : childrenInfo) {
						IndividualInfo child = individualInfoObjMap.get(ret);

						if (child.getBirthDate() == null) {
							// System.out.println("Error: No birthday for child: "+
							// child.getName());
							continue;
						}
						Date childBirth = child.getBirthDate();
						Date childBirthTmp = (Date) childBirth.clone();
						childBirthTmp.setYear((childBirthTmp.getYear() + 1970));
						float diff = childBirthTmp.getTime()
								/ (1000 * 60 * 60 * 24) - momBirthTmp.getTime()
								/ (1000 * 60 * 60 * 24);
						float diffYear = diff / 365;
						if (diffYear > 60.0) {
							System.out.println("Age difference between mom "
									+ mom.getName() + " and child "
									+ child.getName() + " is larger than 60.");
						}
					}
				}
			}
		}
	}

	public static void checkInvidIdForFam(
			HashMap<Integer, FamilyInfo> familyInfoObjMap,
			HashMap<Integer, IndividualInfo> individualInfoObjMap) {
		Global.printTitle("US 33: Check error if everyone in family has own individual id");
		if (familyInfoObjMap != null && !familyInfoObjMap.isEmpty()) {
			Object[] famKey = familyInfoObjMap.keySet().toArray();
			Arrays.sort(famKey);

			for (Object retval : famKey) {
				FamilyInfo fam = (FamilyInfo) familyInfoObjMap.get(retval);
				ArrayList<Integer> thisFam = new ArrayList<Integer>();
				if (fam.getHusband() != 0) {
					thisFam.add(fam.getHusband());
				}
				if (fam.getWife() != 0) {
					thisFam.add(fam.getWife());
				}
				if (fam.getChildren() != null) {
					for (Integer i : fam.getChildren()) {
						thisFam.add(i);
					}
				}
				for (Integer ind : thisFam) {
					IndividualInfo thisIndiv = (IndividualInfo) individualInfoObjMap
							.get(ind);
					ArrayList<Integer> familyNo = thisIndiv.getSpouseOfFamPtr();
					familyNo.add(thisIndiv.getChildOfFamPtr());
					if (!familyNo.contains(retval)) {
						System.out.println("Error: Mismatch of individualID @I"+ ind +" familyID @F"+retval);
					}

				}

			}
		}
		System.out.println();
		System.out.println("***** End of Output for US33 *****");
	}

	public static void checkFamIdForIndiv(
			HashMap<Integer, FamilyInfo> familyInfoObjMap,
			HashMap<Integer, IndividualInfo> individualInfoObjMap) {
		Global.printTitle("US 34: Check error if every individual has family id");
		if (individualInfoObjMap != null && !individualInfoObjMap.isEmpty()) {
			Object[] indivKey = familyInfoObjMap.keySet().toArray();
			Arrays.sort(indivKey);

			for (Object retval : indivKey) {
				IndividualInfo indiv = (IndividualInfo) individualInfoObjMap
						.get(retval);
				ArrayList<Integer> famS = new ArrayList<Integer>();
				Integer famC = 0;
				if (indiv.getChildOfFamPtr() != 0) {
					famC = indiv.getChildOfFamPtr();
					ArrayList<Integer> thisFam = new ArrayList<Integer>();
					FamilyInfo fam = familyInfoObjMap.get(famC);
					thisFam = fam.getChildren();
					if (!thisFam.contains(retval)) {
						System.out.println("Error: Mismatch of individualID @I"+ retval +" familyID @F"+famC);
					}
					if (indiv.getSpouseOfFamPtr() != null) {
						famS = indiv.getSpouseOfFamPtr();
						for (Integer f : famS) {
							FamilyInfo fam1 = (FamilyInfo) familyInfoObjMap
									.get(f);
							ArrayList<Integer> thisFam1 = new ArrayList<Integer>();
							if (fam1.getHusband() != 0)
								thisFam1.add(fam1.getHusband());
							if (fam1.getWife() != 0)
								thisFam1.add(fam1.getWife());
							if (!thisFam1.contains(retval))
								System.out.println("Error: Mismatch of individualID @I"+ retval +" familyID @F"+famS);
						}

					}

				}

			}
		}
		System.out.println();
		System.out.println("***** End of Output for US34 *****");
	}

}
