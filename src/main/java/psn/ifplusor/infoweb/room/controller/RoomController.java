package psn.ifplusor.infoweb.room.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import psn.ifplusor.core.common.CodeAndMessage;
import psn.ifplusor.infoweb.room.persistence.Room;
import psn.ifplusor.infoweb.room.service.RoomService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author james
 * @version 2/11/17
 */
@RestController
@RequestMapping("/room")
public class RoomController {

	@Resource
	private RoomService roomService;

	@RequestMapping("/create")
	public Map create() {
		Map<String, Object> model = new HashMap<>();
		Room room = roomService.create();
		if (room != null) {
			CodeAndMessage.setCodeAndMessage(model, 0, "success");
			model.put("room", room);
		} else {
			CodeAndMessage.setCodeAndMessage(model, 1, "error");
		}
		return model;
	}

	@RequestMapping("/list")
	public Map list() {
		Map<String, Object> model = new HashMap<>();
		List<Room> lstRoom = roomService.list();
		if (lstRoom != null) {
			CodeAndMessage.setCodeAndMessage(model, 0, "success");
			model.put("rooms", lstRoom);
		} else {
			CodeAndMessage.setCodeAndMessage(model, 1, "error");
		}
		return model;
	}
}
