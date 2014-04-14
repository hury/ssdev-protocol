package ctd.protocol.schema.controller;
import ctd.controller.support.AbstractController;
import ctd.protocol.schema.support.container.Segment;

public class SegmentController extends AbstractController<Segment> {
	private static SegmentController instance;
	
	public SegmentController(){
		instance = this;
		setLoader(new SegmentLocalLoader());
	}
	
	public static SegmentController instance(){
		if(instance == null){
			instance = new SegmentController();
		}
		return instance;
	}
	
}
