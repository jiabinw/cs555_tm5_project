package UnitTests;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import cs555_tm5_project.Main;
public class HiteshUnitTest {
	@Test
	public void UnitTestForMarriageAfterDeath()
	{
	Date DeathDate=new Date(2010,02,02);
	Date marriageDate=new Date(2011,02,02);
	Boolean result=DeathDate.before(marriageDate);
	assertEquals(true,result);
	}
	@Test
	public void UnitTestForValidDateCheck()
	{
	Date result=Main.convertStrToDate("31 FEB 2012"); //unit test for invalid Date
	assertEquals(null,result);
	}
}
