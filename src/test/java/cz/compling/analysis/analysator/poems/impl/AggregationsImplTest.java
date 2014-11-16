package cz.compling.analysis.analysator.poems.impl;

import cz.compling.AbstTest;
import cz.compling.analysis.analysator.poems.aggregation.AggregationRule;
import cz.compling.analysis.analysator.poems.aggregation.IAggregation;
import cz.compling.model.Aggregations;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.Normalizer;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 8.3.14 8:06</dd>
 * </dl>
 */
public class AggregationsImplTest extends AbstTest{

	private static IAggregation analyser;

	@BeforeClass
	public static void setUp() throws Exception {

		AbstTest.setUp();

		analyser = getCompLing().poemAnalysis().aggregation();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAggregationFor0() throws Exception {
		analyser.getAggregations().getAggregationForBaseLine(1).getAggregationFor(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAggregationForMaxVal() throws Exception {
		IAggregation aggregation = analyser;
		aggregation.getAggregations().getAggregationForBaseLine(1).getAggregationFor(aggregation.getAggregations().getAggregationForBaseLine(1).getMaxDistance() + 1);
	}

	@Test
	public void testGetAggregationFor() throws Exception {
		IAggregation iAggregation = analyser;

		/*
		1  - V půlnoc kdysi v soumrak čirý chorý bděl jsem sám a sirý,
		10 - po té, již zvou Leonora v nebi, prahla zoufajíc, —

		1
		aaabcccddeehiiijkkllmmmnoooprrrrsssssuuvvyyyy = 45
		ak am as bd ch ci ck de dy el em ho ir ir iv js kč kd lj ln ma mr ms no oc or ou pů ra ry ry ry sa se si si so ul um vp vs yb yc ys = 44

		10
		aaaabceeefhiiijjllnnooooopprrtuuvvzzz = 37
		ah aj av az bi eb ej eo fa hl ic ip iz ji ji la le ne no on or ot ou ou po pr ra ra te uf ul vn vo zo zv zz = 36
		SINGLE:25
		DOUBLE: no or ou ra ul = 5

		 */
		/*
		 * Apply rules to ignore diacritics
		 */
		AggregationRule diacriticsRule = new AggregationRule() {
			@Override
			public boolean matchesRule(String str) {
				//string with diacritics will not match
				return !str.matches("[a-zA-Z]+");
			}

			@Override
			public String compareTo(String str) {
				String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
				return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
			}
		};

		iAggregation.registerRule(diacriticsRule);

		Aggregations.Aggregation.LineAggregation aggregationFor = iAggregation.getAggregations().getAggregationForBaseLine(1).getAggregationFor(10);

		int singleSet1Size = aggregationFor.getSingleSet1Size();
		Assert.assertEquals(45, singleSet1Size);
		int doubleSet1Size = aggregationFor.getDoubleSet1Size();
		Assert.assertEquals(44, doubleSet1Size);

		int singleSet2Size = aggregationFor.getSingleSet2Size();
		Assert.assertEquals(37, singleSet2Size);
		int doubleSet2Size = aggregationFor.getDoubleSet2Size();
		Assert.assertEquals(36, doubleSet2Size);

		int singleSetsIntersectionSize = aggregationFor.getSingleSetsIntersectionSize();
		Assert.assertEquals(25, singleSetsIntersectionSize);
		int doubleSetsIntersectionSize = aggregationFor.getDoubleSetsIntersectionSize();
		Assert.assertEquals(5, doubleSetsIntersectionSize);

	    /*
		1  - V půlnoc kdysi v soumrak čirý chorý bděl jsem sám a sirý,
		34 - Tiše, srdce! podívám se na zjev u svých okenic!

		1
		aaabcccddeehiiijkkllmmmnoooprrrrsssssuuvvyyyy = 45
		ak am as bd ch ci ck de dy el em ho ir ir iv js kč kd lj ln ma mr ms no oc or ou pů ra ry ry ry sa se si si so ul um vp vs yb yc ys = 44

		34
		aacccddeeeeehiiijkmnnooprsssstuvvvyz = 36
		am az ce ch dc di en en ep es ev ho iv is iv je ke ms na ni od ok po rd se se sr sv ti us va vu vy yc zj = 35

		SINGLE: aacccddeehiiijkmnooprssssuvvy = 29
		DOUBLE: am ch ho iv ms se yc = 7
		*/
		aggregationFor = iAggregation.getAggregations().getAggregationForBaseLine(1).getAggregationFor(34);
		singleSet2Size = aggregationFor.getSingleSet2Size();
		Assert.assertEquals(36, singleSet2Size);
		doubleSet2Size = aggregationFor.getDoubleSet2Size();
		Assert.assertEquals(35, doubleSet2Size);

		singleSetsIntersectionSize = aggregationFor.getSingleSetsIntersectionSize();
		Assert.assertEquals(29, singleSetsIntersectionSize);
		doubleSetsIntersectionSize = aggregationFor.getDoubleSetsIntersectionSize();
		Assert.assertEquals(7, doubleSetsIntersectionSize);

		/*
		 1   - V půlnoc kdysi v soumrak čirý chorý bděl jsem sám a sirý,
		 100 - Přestaň rvát mi srdce, šelmo, kliď se z dveří prchajíc!“

		 1
		 aaabcccddeehiiijkkllmmmnoooprrrrsssssuuvvyyyy = 45
		 ak am as bd ch ci ck de dy el em ho ir ir iv js kč kd lj ln ma mr ms no oc or ou pů ra ry ry ry sa se si si so ul um vp vs yb yc ys = 44

		100
		aaacccdddeeeeehiiiijkllmmnopprrrrrssssttvvz = 43
		aj an at ce ch dc ds dv el es er es ez ha ic id ip is ji kl li lm mi mo nr ok pr pr rc rd re ri rv se se sr st ta tm va ve zd = 42¨

		SINGLE: aaacccddeehiiijkllmmnoprrrrssssvv = 33
		DOUBLE: ch el se = 3
	     */
		aggregationFor = iAggregation.getAggregations().getAggregationForBaseLine(1).getAggregationFor(100);
		singleSet2Size = aggregationFor.getSingleSet2Size();
		Assert.assertEquals(43, singleSet2Size);
		doubleSet2Size = aggregationFor.getDoubleSet2Size();
		Assert.assertEquals(42, doubleSet2Size);

		singleSetsIntersectionSize = aggregationFor.getSingleSetsIntersectionSize();
		Assert.assertEquals(33, singleSetsIntersectionSize);
		doubleSetsIntersectionSize = aggregationFor.getDoubleSetsIntersectionSize();
		Assert.assertEquals(3, doubleSetsIntersectionSize);
	}

	@Test
	public void testGetMaxDistance() throws Exception {
		IAggregation IAggregation = analyser;
		int maxDistance = IAggregation.getAggregations().getAggregationForBaseLine(1).getMaxDistance();

		Assert.assertEquals(107, maxDistance);
	}

}
