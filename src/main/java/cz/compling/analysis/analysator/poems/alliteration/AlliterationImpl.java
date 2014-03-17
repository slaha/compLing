package cz.compling.analysis.analysator.poems.alliteration;

import cz.compling.model.Alliteration;
import cz.compling.rules.BaseRuleHandler;
import cz.compling.rules.RuleHandler;
import cz.compling.rules.RuleObserver;
import cz.compling.text.TextImpl;
import cz.compling.text.poem.Poem;
import cz.compling.text.poem.PoemImpl;
import cz.compling.text.poem.Verse;

import java.util.Collection;

/**
 *
 * TODO 
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 2.3.14 17:59</dd>
 * </dl>
 */
public class AlliterationImpl implements IAlliteration {

	private final Poem poem;

	private final Alliteration alliteration;

	private final RuleHandler<AlliterationRule> ruleHandler;

	private int numberOfVerses;
	private boolean dirty;

	public AlliterationImpl(Poem poem) {
		this.poem = poem;
		alliteration = new Alliteration();
		numberOfVerses = 0;
		ruleHandler = new BaseRuleHandler<AlliterationRule>();
		compute();
		dirty = false;
	}

	private void compute() {
		Collection<Verse> verses = poem.getVerses();

		for (Verse verse : verses) {
			Alliteration.LineAlliteration allit = new Alliteration.LineAlliteration(++numberOfVerses);
			for (String word : verse.getWords()) {
				String firstChar = applyRules(word);
				if (firstChar != null) {
					allit.add(firstChar);
				}
			}
			alliteration.put(numberOfVerses, allit);
		}
	}

	private String applyRules(String word) {
		try {
			word = trimToFirstChar(word);
			String toSave;

			for (AlliterationRule alliterationRule : getRegisteredRules()) {
				if ((toSave = alliterationRule.modify(word)) != null) {
					//..rule matches, reference is filled by char(s)
					return toSave.toLowerCase();
				}
			}
			//..no rule, return first char
			return String.valueOf(Character.toLowerCase(word.charAt(0)));
		} catch (IllegalStateException ex) {
			//..just ignore
		}

		return null;
	}

	private String trimToFirstChar(String word) {
		word = word.trim();
		final int length = word.length();
		if (length == 0) {
			throw new IllegalStateException("Param word is empty after trimming");
		}

		int firstCharIndex = 0;
		while (!Character.isLetter(word.charAt(firstCharIndex))) {
			firstCharIndex++;
			if (firstCharIndex >= length) {
				String msg = String.format("Cannot find firs character in '%s'", word);
				throw new IllegalStateException(msg);
			}
		}
		return word.substring(firstCharIndex);
	}

	@Override
	public Alliteration getAlliteration() {
		if (dirty) {
			numberOfVerses = 0;
			alliteration.clean();
			compute();
			dirty = false;
		}
		return alliteration;
	}

	public static void main(String[] args) {

		String s = "V půlnoc kdysi v soumrak čirý chorý bděl jsem sám a sirý,\n" +
			"v knihy staré, zapomněné ukláněl jsem bledou líc;\n" +
			"skoro schvátilo mne spaní, an ruch lehký znenadání\n" +
			"ozval se jak zaklepání na mé dvéře a zas nic.\n" +
			"„Chodec to snad,“ pravím k sobě, klepá to a více nic,\n" +
			"    pouze to, a pranic víc.“\n" +
			"\n" +
			"Jasně pamět má mi praví, prosinec byl chmurný, tmavý,\n" +
			"z krbu mnohá jiskra v žhavý let se dala prchajíc.\n" +
			"Prahnul jsem po kuropění, marně těchy hledal v čtení\n" +
			"pro tu, které více není; duše moje zoufajíc\n" +
			"po té, již zvou Leonora v nebi, prahla zoufajíc, —\n" +
			"    zde ji nezná nikdo víc.\n" +
			"\n" +
			"Hedvábná se hnula clona, cos v ní šustí, cos v ní stoná,\n" +
			"mysl plní se a vlní, bázně také neznajíc,\n" +
			"takže srdce si dodávám, vstávám a si předříkávám:\n" +
			"„Chodec to, ždá nocleh sobě, bloudě kol mých okenic,\n" +
			"nějaký to chodec pozdní bloudí u mých okenic —\n" +
			"    to je to a pranic víc.“\n" +
			"\n" +
			"Duše má nabyla síly, neváhal jsem ani chvíli:\n" +
			"„Pane,“ děl jsem, „nebo paní, nemyslete na to víc,\n" +
			"pravda jest, mne zmohlo spaní, neslyšel jsem znenadání\n" +
			"vaše lehké zaklepání na mé dvéře a pak nic,\n" +
			"myslil jsem, že nic to není.“ — Otevru, — aj, ticho, — nic;\n" +
			"    venku tma a pranic víc.\n" +
			"\n" +
			"Dlouho do tmy patřím snivý, stojím, duše má se diví,\n" +
			"v strachu sny si, jaké nikdo ještě nesnil, spřádajíc.\n" +
			"Ticho kolem nezrušeno, leč v tom tichu jedno jméno\n" +
			"Leonoro? vysloveno šeptem znělo a víc nic;\n" +
			"já ho šeptal. — „Leonoro!“ nazpět znělo zmírajíc.\n" +
			"    Pouze to a pranic víc.\n" +
			"\n" +
			"Nazpět v komnatu se vracím, žhavou duší v sny se ztrácím.\n" +
			"Záhy zas ten klepot slyším, hlasněji než dřív, a víc.\n" +
			"„Jistě,“ pravím, „jistě kdosi klepá a mne o vstup prosí;\n" +
			"podívám se, jaký divný zjev to u mých okenic.\n" +
			"Tiše, srdce! podívám se na zjev u svých okenic!\n" +
			"    Je to vítr a nic víc!“\n" +
			"\n" +
			"Otevru své okno, rázem vířivým tu letem na zem\n" +
			"snesl se a kráčí havran statný, jakých není víc,\n" +
			"ani trochu nepozdravil, na chvilku se nezastavil,\n" +
			"na dvéře — jak pán neb paní, tak měl při tom vážnou líc —\n" +
			"nad Pallady used sochu, a měl stále vážnou líc,\n" +
			"    sedl, mlčel a nic víc.\n" +
			"\n" +
			"Černý pták ten ve svém zjevu vážný tak a v přísném hněvu\n" +
			"zjasnil trochu do úsměvu moji truchlou, smutnou líc.\n" +
			"„Ač máš hřeben ostříhaný,“ děl jsem, „nejsi sketa planý,\n" +
			"stará šelmo, z pekel brány bouří štvaná prchajíc.\n" +
			"Kterak sluješ, břehem pekla v jizbu moji prchajíc?\n" +
			"    Na to havran: „Nikdy víc!“\n" +
			"\n" +
			"S žasem zřím na toho ptáka, co to nemotora kdáká;\n" +
			"ač z té jeho odpovědi nemoh jsem si vybrat nic,\n" +
			"však to dlužno uvážiti: zřídka se to stane v žití,\n" +
			"ptáka že je možno zříti, jenž by sletěl s okenic,\n" +
			"ptáka nebo zvíře, které na sochu si sedajíc\n" +
			"    zvalo by se: ‚Nikdy víc‘!\n" +
			"\n" +
			"Pták na tiché soše seděl, pouze jedno slovo věděl,\n" +
			"jeho duše v tom jen byla, jiné řeči neznajíc,\n" +
			"neřek více slovo jiné, nezčeřil své peří stinné,\n" +
			"až já zavzdych: „Vše tak mine, druhů měl jsem na tisíc,\n" +
			"také ten tam opustí mne nadějí jak na tisíc!“\n" +
			"    Havran pravil: „Nikdy víc!“\n" +
			"\n" +
			"Moudrou řečí tou když zrušil kolem ticho, já jsem tušil:\n" +
			"„Jistě mimo toto slovo chudák havran neví nic,\n" +
			"jistě stihla jeho pána osudu za ranou rána,\n" +
			"takže duše jeho štvána v refrén jeden zoufajíc\n" +
			"pohřbila svou naděj všecku v smutný refrén zoufajíc,\n" +
			"    v refrén: Nikdy, nikdy víc!\n" +
			"\n" +
			"Posud havran ve svém zpěvu žal můj měnil do úsměvu,\n" +
			"nyní židli pošinul jsem k dveřím, k soše, jemu v líc,\n" +
			"zvolaa klesám do sametu, duma dumu stíhá v letu,\n" +
			"v myšlénkách mi sjede z retů: „Šelma ta, zlo zvěstujíc,\n" +
			"co chce příšerná a šerá šelma ta zlo zvěstujíc,\n" +
			"    krákající: Nikdy víc?“\n" +
			"\n" +
			"V domněnky jsem upad zcela, duše má se zamlčela,\n" +
			"zřítelnice ptáka zřela v srdce moje hárajíc,\n" +
			"zvědět víc má chuť se nesla, skráň má pohodlně klesla\n" +
			"v měkký samet mého křesla, lampy zář kam šlehajíc\n" +
			"fialový samet křesla osvítila, šlehajíc,\n" +
			"    kam nesedne ona víc!\n" +
			"\n" +
			"Ve vzduchu se vůně chvěla, jak by z kaditelnic spěla\n" +
			"andělů, jichž kroků echo znělo chodbou zmírajíc.\n" +
			"„Věru,“ křiknu, „V tvoji těchu anděly bůh seslal v spěchu,\n" +
			"by tvá duše v moři vzdechů nehynula zoufajíc,\n" +
			"Leonoru zapomněla v moři vzdechů zoufajíc!“ —\n" +
			"    Na to havran: „Nikdy vic!“\n" +
			"\n" +
			"„Proroku!“ dím, „čárných zraků, věštče, ďáble nebo ptáku,\n" +
			"Satan slal tě, či bouř z mraků štvala tě se vztekajíc\n" +
			"bídného, však duše chladné v kouzelné ty kraje zrádné,\n" +
			"na tu zem, kde hrůza vládne, rci mi pravdu a víc nic,\n" +
			"jest v Gilead balzám těchy, jen to rci mi, a víc nic!“\n" +
			"    Na to havran: „Nikdy víc!“\n" +
			"\n" +
			"„Proroku,“ dím, „čárných zraků, věštče, ďáble nebo ptáku,\n" +
			"pro nebe i boha prosím, jejž i ty ctíš, těkajíc\n" +
			"duše mořem trudu, hněvu, zda obejme zářnou děvu,\n" +
			"Leonoru, kterou v zpěvu andělů zve na tisíc,\n" +
			"zářící tu krásnou děvu, zdali uzřím lící v líc?“\n" +
			"    Na to havran: „Nikdy víc!“\n" +
			"\n" +
			"„Však teď vari s mého prahu, ptáku, ďáble, starý vrahu,\n" +
			"v peklo zpět neb v bouř, jež hnala sem tě do mých okenic,\n" +
			"ani péra nenech tady, ani slova z lži tvé, zrády,\n" +
			"v samotě mé beze vnady nech mne, nechci vědět nic.\n" +
			"Přestaň rvát mi srdce, šelmo, kliď se z dveří prchajíc!“\n" +
			"    Na to havran: „Nikdy víc!“\n" +
			"\n" +
			"Ale havran neslet, sedí, stále sedí, na mne hledí,\n" +
			"nade dveřmi sochy bílé černým křídlem stíní líc,\n" +
			"jeho očí lesk mne svírá, démon spící takto zírá,\n" +
			"lampy zář jej obestírá jeho stín kol vrhajíc,\n" +
			"a má duše z toho stínu nevzchopí se zoufajíc,\n" +
			"    nevzchopí se — nikdy víc!\n";

		IAlliteration aggregation1 = new AlliterationImpl(new PoemImpl(new TextImpl(s)));
		for (int i = 1; i <= aggregation1.getAlliteration().getVerseCount(); i++) {
			System.out.println(aggregation1.getAlliteration().getAlliterationFor(i));
		}
	}

	@Override
	public Iterable<AlliterationRule> getRegisteredRules() {
		return ruleHandler.getRegisteredRules();
	}

	@Override
	public void registerRule(AlliterationRule rule) {
		ruleHandler.registerRule(rule);
		dirty = true;
	}

	@Override
	public boolean removeRule(AlliterationRule rule) {
		if (ruleHandler.removeRule(rule)) {
			dirty = true;
			return true;
		}
		return false;
	}

	@Override
	public void registerRuleObserver(RuleObserver<AlliterationRule> observer) {
		ruleHandler.registerRuleObserver(observer);
	}

	@Override
	public boolean unregisterRuleObserver(RuleObserver<AlliterationRule> observer) {
		return ruleHandler.unregisterRuleObserver(observer);
	}

	@Override
	public Iterable<RuleObserver<AlliterationRule>> getRegisteredObserves() {
		return ruleHandler.getRegisteredObserves();
	}
}
