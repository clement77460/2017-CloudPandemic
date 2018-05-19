package fr.efrei.nouvellonJaworski.model.abstractmodel;

import javax.swing.table.AbstractTableModel;

import fr.efrei.paumier.shared.simulation.Statistics;

public class TableModelPandemic extends AbstractTableModel {

	private static final long serialVersionUID = 1000045L;
	private String[] columnNames= {"Temps","Original","Alive","Quarantined","Infected","Dead","Money","Panic LVL"};
    private Object[][] data;
    

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    @Override
    public int getRowCount() {
        return data.length;
    }
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
    @Override
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }
    @Override
    public Class<?> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
    @Override
    /**
     * permet de dire quelles cellules sont modifiables ou non
     * 
     */
    public boolean isCellEditable(int row, int col) {
        return false;
    }
    /**
     * permet de remplir notre tableau
     * @param data
     * @param title 
     */
    public void setDataVector(Object[][] data,String[] title){
       this.data=data;
       this.columnNames=title;
    }
    
    
    @Override
    /**
     * permet de mettre une valeur dans une cellule spécifique
     */
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
    
    public void fillTableWithStats(Statistics stats) {
    	Object temp[][]=new Object[1][this.getColumnCount()];
    	temp[0][0]=stats.getEllapsedDuration();
    	temp[0][1]=stats.getOriginalPopulation();
    	temp[0][2]=stats.getLivingPopulation();
    	temp[0][3]=stats.getQuarantinedPopulation();
    	temp[0][4]=stats.getInfectedPopulation();
    	temp[0][5]=stats.getDeadPopulation();
    	temp[0][6]=stats.getMoney();
    	temp[0][7]=stats.getPanicLevel();
    	
    	this.setDataVector(temp,this.columnNames);
    }
    
}
