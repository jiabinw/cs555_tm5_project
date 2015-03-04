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
					if(dadInfo.getBirthDate()!=null && momInfo.getBirthDate()!=null){
					Date dadBirth = (Date) dadInfo.getBirthDate();
					Date momBirth = (Date) momInfo.getBirthDate();
         
					

					if (famInfo.getChildren() != null) {
						
						ArrayList<Integer> children = famInfo.getChildren();

						for (Integer child : children) {
							
							IndividualInfo childInfo = (IndividualInfo) individualInfo
									.get(child);
							if(childInfo.getBirthDate()!=null){
							Date childBirth = childInfo.getBirthDate();

							if (!(childBirth.after(momBirth) && childBirth
									.after(dadBirth))) {
								String childId = Global.rebuildIdentifier(
										Integer.toString(child), 'I');

								System.out.println("Wrong birthday for child :"
										+ childId + childInfo.getName());}
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
		
		
	}

