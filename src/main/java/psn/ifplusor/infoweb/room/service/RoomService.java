package psn.ifplusor.infoweb.room.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import psn.ifplusor.core.common.IdGenerator;
import psn.ifplusor.core.common.MapUtil;
import psn.ifplusor.infoweb.room.persistence.Room;
import psn.ifplusor.infoweb.security.Service.UserService;
import psn.ifplusor.infoweb.security.persistence.User;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author james
 * @version 2/11/17
 */
@Service
public class RoomService {

	private static final Logger logger = LoggerFactory.getLogger(RoomService.class);

	private Map<Long, Room> htIdToRoom = new ConcurrentHashMap<>();
	private Map<Long, Set<Long>> htUidToRooms = new ConcurrentHashMap<>();

	@Resource
	private UserService userService;

	private User getCurrentUser() {
		logger.debug("get current user entity.");
		return userService.getCurrentUser();
	}

	public Room create() {
		long myUid = getCurrentUser().getId();

		long id = IdGenerator.get().nextId();
		while (htIdToRoom.get(id) != null) {
			id = IdGenerator.get().nextId();
		}

		Room room = new Room(id, myUid);
		htIdToRoom.put(id, room); // 依赖 IdGenerator 保证 id 全局唯一
		MapUtil.addToSetMap(htUidToRooms, myUid, id);

		return room;
	}

	public List<Room> list() {
		long myUid = getCurrentUser().getId();

		List<Room> lstRoom = new ArrayList<>();
		Set<Long> setRoom = htUidToRooms.get(myUid);
		if (setRoom != null) {
			for (Long id : setRoom) {
				Room room = htIdToRoom.get(id);
				if (room != null) {
					lstRoom.add(room);
				}
			}
		}

		return lstRoom;
	}
}
