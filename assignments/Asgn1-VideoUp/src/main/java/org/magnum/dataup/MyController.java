/*
 * 
 * Copyright 2014 Jules White
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.magnum.dataup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.magnum.dataup.model.Video;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {
	
	public static final String DATA_PARAMETER = "data";

	public static final String ID_PARAMETER = "id";

	public static final String VIDEO_SVC_PATH = "/video";
	
	public static final String VIDEO_DATA_PATH = VIDEO_SVC_PATH + "/{id}/data";
	
	//private List<Video> videos = new ArrayList<Video>();
    private static final AtomicLong currentId = new AtomicLong(0L);

	private Map<Long,Video> videos = new HashMap<Long, Video>();
	private List<Video> videoList = new ArrayList<Video>(videos.values());

  	public Video save(Video entity) {
		checkAndSetId(entity);
		videos.put(entity.getId(), entity);
		return entity;
	}

	private void checkAndSetId(Video entity) {
		if(entity.getId() == 0){
			entity.setId(currentId.incrementAndGet());
		}
	}
	//TODO Post method mapping
	@RequestMapping(value=VIDEO_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody Video addVideo(@RequestBody Video v){
//		v.setId(hashCode());
		save(v);
	return v;
	}
	
	//TODO Get method mapping 
	@RequestMapping(value=VIDEO_SVC_PATH, method=RequestMethod.GET)
	public @ResponseBody List<Video> getVideos(){
		return videoList;
	}
	
	//TODO Think about multipart getVideo implementation
	@RequestMapping (value=VIDEO_DATA_PATH, method=RequestMethod.POST)
	public @ResponseBody Video addVideoData(@RequestBody Video v){
		save(v);
		return v;
	}
	
	@RequestMapping (value=VIDEO_DATA_PATH, method=RequestMethod.GET)
	public @ResponseBody Map<Long,Video> getVideoData(){
		return videos;
	}


}
