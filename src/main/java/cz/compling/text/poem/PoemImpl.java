package cz.compling.text.poem;

import cz.compling.text.Line;
import cz.compling.text.Text;
import cz.compling.text.TextImpl;
import cz.compling.utils.Reference;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * Basic implementation of {@code Poem} interface
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 3.3.14 7:06</dd>
 * </dl>
 */
public class PoemImpl implements Poem {

	private final Text text;

	private final TIntObjectMap<Strophe> strophes;

	private int countOfStrophes;

	public PoemImpl(Text text) {
		this.text = text;
		strophes = new TIntObjectHashMap<Strophe>();
		fillStrophes();
	}

	private void fillStrophes() {
		Collection<Line> lines = text.getLines();

	final List<Verse> verses = new ArrayList<Verse>();

		final Reference<Integer> currentStrophe = new Reference<Integer>(1);

		Runnable addToStrophes = new Runnable() {

			@Override
			public void run() {
				if (!verses.isEmpty()) {
					//..save verses to strophes
					strophes.put(currentStrophe.value, new StropheImpl(currentStrophe.value, new ArrayList<Verse>(verses)));
					currentStrophe.value++;
					verses.clear();
				}
			}
		};

		for (Line verse : lines) {
			final String verseString = verse.toString();
			if (StringUtils.isNoneBlank(verseString)) {
				//..not empty line → verse
				verses.add(new Verse(verse));

			} else {

				//..empty line → new verses
				addToStrophes.run();
			}
		}

		//..add last verses
		addToStrophes.run();

		countOfStrophes = currentStrophe.value - 1;
	}

	@Override
	public String getPlainText() {
		return text.getPlainText();
	}

	@Override
	public List<Verse> getVerses() {
		List<Verse> verses = new ArrayList<Verse>();

		for (int i = 1; i <= getCountOfStrophes(); i++) {
			Strophe strophe = strophes.get(i);
			verses.addAll(strophe.getVerses());
		}
		
		return verses;
	}

	@Override
	public int getCountOfStrophes() {
		return countOfStrophes;
	}

	@Override
	public String getStrophe(int strophe) {
		Collection<Verse> versesOfStrophe = getVersesOfStrophe(strophe);
		StringBuilder stropheText = new StringBuilder();
		for (Verse verse : versesOfStrophe) {
			stropheText
				.append(verse)
				.append('\n');
		}
		return stropheText.toString();
	}

	@Override
	public Collection<Strophe> getStrophes() {
		final List<Strophe> str = new ArrayList<Strophe>(strophes.valueCollection());
		Collections.sort(str);
		return Collections.unmodifiableList(str);
	}

	@Override
	public Collection<Verse> getVersesOfStrophe(int strophe) {
		if (strophe < 1 || strophe > getCountOfStrophes()) {
			String msg = String.format("Param strophe cannot be less than 1 or bigger than getCountOfStrophes()=%d. Was %d", getCountOfStrophes(), strophe);
			throw new IllegalArgumentException(msg);
		}
		return strophes.get(strophe).getVerses();
	}

	@Override
	public Poem applyRule(PoemModificationRule rule) {
		String modifiedPoem = rule.modify(this);
		Text text = new TextImpl(modifiedPoem);
		return new PoemImpl(text);
	}
}
