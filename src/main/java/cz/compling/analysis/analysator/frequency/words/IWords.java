package cz.compling.analysis.analysator.frequency.words;

import cz.compling.model.Words;
import cz.compling.rules.RuleHandler;
import cz.compling.text.Text;
import cz.compling.text.TextModificationRule;

/**
 *
 * Methods that must be implemented for analysing frequency of words in text
 *
 * <dl>
 * <dt>Created by:</dt>
 * <dd>slaha</dd>
 * <dt>On:</dt>
 * <dd> 1.3.14 11:19</dd>
 * </dl>
 */
public interface IWords extends RuleHandler<WordFrequencyRule> {

	Words getWords();

	public static final TextModificationRule SPLIT_TO_WORDS = new TextModificationRule() {
		@Override
		public String modify(Text text) {
			String plainText = text.getPlainText();
			StringBuilder sb = new StringBuilder(plainText.length());
			final char[] chars = plainText.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				char c = chars[i];
				if (Character.isLetter(c)) {
					sb.append(c);
					continue;

				} else if (i > 0 && i < (chars.length - 1)) {
					//..we are not at start onŕ in the end
					char prev = chars[i - 1];
					char next = chars[i + 1];
					if (Character.isLowerCase(prev) && Character.isLetter(prev)
						&& Character.isLowerCase(next) && Character.isLetter(next)) {
						sb.append(c);
						continue;
					}
				}
				sb.append(' ');
			}
			return sb.toString().trim();
		}
	};
}
