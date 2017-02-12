package psn.ifplusor.infoweb.room.persistence;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author james
 * @version 2/11/17
 */
public class Room {

	private long id;
	private long owner;

	public enum MemberStatus {
		online,
		offline
	}

	public class MemberInfo {
		final long uid;

		MemberStatus status = MemberStatus.offline;
		String media = "";

		MemberInfo(long uid) {
			this.uid = uid;
		}

		public MemberStatus getStatus() {
			return status;
		}

		public String getMedia() {
			return media;
		}
	}

	private Map<Long, MemberInfo> memberInfo = new ConcurrentHashMap<>();

	public Room(long id, long owner) {
		this.id = id;
		this.owner = owner;
		memberInfo.put(owner, new MemberInfo(owner));
	}

	public long getId() {
		return id;
	}

	public long getOwner() {
		return owner;
	}

//	public Map<Long, MemberInfo> getMemberInfo() {
//		return memberInfo;
//	}
}
