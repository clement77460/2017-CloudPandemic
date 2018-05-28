package fr.efrei.nouvellonJaworski.model.abstractmodel;

import javax.swing.table.AbstractTableModel;

import fr.efrei.paumier.shared.simulation.Statistics;

public class TableModelPandemic extends AbstractTableModel {

	private static final long serialVersionUID = 1000045L;
	private String[] columnNames= {"Groupe","Temps","Original","Alive","Quarantined","Infected","Dead","Money","Panic LVL"};
    private Object[][] data;
    
    public TableModelPandemic() {
    }
    
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    @Override
    public int getRowCount() {
    	if(data==null) {
    		return 0;
    	}
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
    public void setDataVector(Object[][] data){
       this.data=data;
       this.fireTableDataChanged();
    }
    
    
    @Override
    /**
     * permet de mettre une valeur dans une cellule spécifique
     */
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
    
    
    public void refreshStats(Statistics stats,String groupName) {
    	int row=this.isPresent(groupName);
    	if(row >=0) {
    		this.fillTableWithStats(stats, row);
    	}
    	else {
    		this.fillTableWithNewStats(stats, groupName);
    	}
    }
    
    private void fillTableWithStats(Statistics stats,int row) {

    	
    	data[row][1]=stats.getEllapsedDuration();
    	data[row][2]=stats.getOriginalPopulation();
   		data[row][3]=stats.getLivingPopulation();
   		data[row][4]=stats.getQuarantinedPopulation();
   		data[row][5]=stats.getInfectedPopulation();
   		data[row][6]=stats.getDeadPopulation();
   		data[row][7]=stats.getMoney();
   		data[row][8]=stats.getPanicLevel();
    	
    	this.fireTableRowsUpdated(row, row);
    }
    /**
     * 
     * @param groupName
     * @return un indice >=0 si le groupe existe déja
     * 		   un indice < 0 si le groupe n'existe pas
     */
    private int isPresent(String groupName) {
    	for(int i=0;i<this.getRowCount();i++) {
    		if(this.data[i][0].equals(groupName)){
    			return i;
    		}
    	}
    	return -1;
    }
    
    private void fillTableWithNewStats(Statistics stats,String groupName){
    	int row=this.getRowCount();
    	
    	Object[][] temp=new Object[row+1][this.getColumnCount()];
    	
    	for(int i=0;i<row;i++) {
    		for(int j=0;j<this.getColumnCount();j++)
    			temp[i][j]=data[i][j];
    	}
    	temp[row][0]=groupName;
    	temp[row][1]=stats.getEllapsedDuration();
    	temp[row][2]=stats.getOriginalPopulation();
    	temp[row][3]=stats.getLivingPopulation();
    	temp[row][4]=stats.getQuarantinedPopulation();
    	temp[row][5]=stats.getInfectedPopulation();
    	temp[row][6]=stats.getDeadPopulation();
    	temp[row][7]=stats.getMoney();
    	temp[row][8]=stats.getPanicLevel();
    	
    	this.setDataVector(temp);
    }
    
}
