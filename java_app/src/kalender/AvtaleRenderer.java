package kalender;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class AvtaleRenderer implements TableCellRenderer{

	public AvtaleRenderer(){
	
	}
		
	
	protected DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO Auto-generated method stub
		JLabel renderer = (JLabel) defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		renderer = new JLabel("TestAvtale");
		
		return renderer;
	}
	
	
	
	
	
	
}
