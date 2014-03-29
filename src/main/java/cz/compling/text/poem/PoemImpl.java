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

	private final TIntObjectMap<String[]> strophes;

	private int countOfStrophes;

	public PoemImpl(Text text) {
		this.text = text;
		strophes = new TIntObjectHashMap<String[]>();
		fillStrophes();
	}

	private void fillStrophes() {
		Collection<Line> lines = text.getLines();

		final List<String> strophe = new ArrayList<String>();

		final Reference<Integer> currentStrophe = new Reference<Integer>(1);

		Runnable addToStrophes = new Runnable() {

			@Override
			public void run() {
				if (!strophe.isEmpty()) {
					//..save strophe to strophes
					strophes.put(currentStrophe.value, strophe.toArray(new String[strophe.size()]));
					currentStrophe.value++;
					strophe.clear();
				}
			}
		};

		for (Line verse : lines) {
			final String verseString = verse.toString();
			if (StringUtils.isNoneBlank(verseString)) {
				//..not empty line → verse
				strophe.add(verseString);

			} else {

				//..empty line → new strophe
				addToStrophes.run();
			}
		}

		//..add last strophe
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
			String[] versesOfStrophe = strophes.get(i);
			toVerseCollection(verses, versesOfStrophe);
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
	public Collection<Verse> getVersesOfStrophe(int strophe) {
		if (strophe < 1 || strophe > getCountOfStrophes()) {
			String msg = String.format("Param strophe cannot be less than 1 or bigger than getCountOfStrophes()=%d. Was %d", getCountOfStrophes(), strophe);
			throw new IllegalArgumentException(msg);
		}
		String[] verses = strophes.get(strophe);
		return toVerseCollection(verses);
	}

	@Override
	public Poem applyRule(PoemModificationRule rule) {
		String modifiedPoem = rule.modify(this);
		Text text = new TextImpl(modifiedPoem);
		return new PoemImpl(text);
	}

	private Collection<Verse> toVerseCollection(String[] verses) {
		return toVerseCollection(new ArrayList<Verse>(), verses);
	}

	private Collection<Verse> toVerseCollection(Collection<Verse> collection, String[] verses) {
		for (String verse : verses) {
			collection.add(new Verse(verse));
		}
		return collection;
	}
}
