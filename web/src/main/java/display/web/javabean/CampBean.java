package display.web.javabean;


import business.dtos.CampDTO;

public class CampBean implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	private CampDTO camp;

	public CampDTO getCamp() {
		return camp;
	}

	public void setCamp(CampDTO camp) {
		this.camp = camp;
	}
}
