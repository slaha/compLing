package cz.compling.poem;

import cz.compling.rules.TextModificationRule;
import cz.compling.text.Text;
import cz.compling.utils.Reference;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
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
		Collection<? extends String> lines = text.getLines();


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

		for (String verse : lines) {
			if (StringUtils.isNoneBlank(verse)) {
				//..not empty line → verse
				strophe.add(verse);

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
	public String applyRule(TextModificationRule rule) {
		return text.applyRule(rule);
	}

	@Override
	public Collection<? extends String> getVerses() {
		Collection<String> verses = new ArrayList<String>();

		for (int i = 1; i <= getCountOfStrophes(); i++) {
			String[] versesOfStrophe = strophes.get(i);
			verses.addAll(Arrays.asList(versesOfStrophe));
		}
		
		return verses;
	}

	@Override
	public int getCountOfStrophes() {
		return countOfStrophes;
	}

	@Override
	public String getStrophe(int strophe) {
		Collection<? extends String> versesOfStrophe = getVersesOfStrophe(strophe);
		StringBuilder stropheText = new StringBuilder();
		for (String verse : versesOfStrophe) {
			stropheText
				.append(verse)
				.append('\n');
		}
		return stropheText.toString();
	}

	@Override
	public Collection<? extends String> getVersesOfStrophe(int strophe) {
		if (strophe < 1 || strophe > getCountOfStrophes()) {
			String msg = String.format("Param strophe cannot be less than 1 or bigger than getCountOfStrophes()=%d. Was %d", getCountOfStrophes(), strophe);
			throw new IllegalArgumentException(msg);
		}
		String[] verses = strophes.get(strophe);
		return Arrays.asList(verses);
	}
}
