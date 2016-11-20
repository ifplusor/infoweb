package psn.ifplusor.infoweb.bpm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author james
 * @version 10/24/16
 */

@Controller
@RequestMapping("/bpm")
public class BpmController {

	@Resource
	RepositoryService repositoryService;

	@Resource
	IdentityService identityService;

	@Resource
	ObjectMapper objectMapper;

	@RequestMapping("/create")
	public ModelAndView create() {

		try {
			ObjectNode editorNode = objectMapper.createObjectNode();
			editorNode.put("id", "canvas");
			editorNode.put("resourceId", "canvas");

			ObjectNode stencilSetNode = objectMapper.createObjectNode();
			stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
			editorNode.set("stencilset", stencilSetNode);

			Model modelData = repositoryService.newModel();

			ObjectNode modelObjectNode = objectMapper.createObjectNode();
			modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, "name_" + UUID.randomUUID());
			modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, "description");
			modelData.setMetaInfo(modelObjectNode.toString());

			modelData.setName("name_" + UUID.randomUUID());
			modelData.setKey("key_" + UUID.randomUUID());

			repositoryService.saveModel(modelData);
			repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("modelId", modelData.getId());

			return new ModelAndView("redirect:/modeler.html", model);
		} catch (Exception e) {
			return new ModelAndView("index");
		}
	}

	@RequestMapping("/list")
	public ModelAndView list() {

		List<Model> lstActModels = repositoryService.createModelQuery().list();
		List<User> lstActUsers = identityService.createUserQuery().list();

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ActModels", lstActModels);
		model.put("ActUsers", lstActUsers);

		return new ModelAndView("bpm/list", model);
	}
}
