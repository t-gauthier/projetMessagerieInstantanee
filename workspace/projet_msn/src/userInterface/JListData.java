package userInterface;

import java.util.Map.Entry;

import javax.swing.JComponent;

/**
 * 
 * @author Dorian, Mickaël, Raphaël, Thibault
 * 
 */
public class JListData extends JComponent implements Entry<String, String>
{

	private static final long serialVersionUID = 7871773513120449008L;
	private final String key;
	private String value;

	public JListData(String key, String value)
	{
		this.key = key;
		this.value = value;
	}

	@Override
	public String getKey()
	{
		return this.key;
	}

	@Override
	public String getValue()
	{
		return this.value;
	}

	@Override
	public String setValue(String value)
	{
		this.value = value;
		return this.value;
	}

	@Override
	public String toString()
	{
		return this.value;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof JListData))
		{
			return false;
		} else if (obj == this)
		{
			return true;
		} else if (!this.key.equals(((JListData) obj).key))
		{
			return false;
		} else if (!this.value.equals(((JListData) obj).value))
		{
			return false;
		}
		return true;
	}
}
