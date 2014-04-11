/*
 * Copyright (C) 2012 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.nttuyen.news.feed;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;

public class FeedReader {
	protected List<FeedProvider> providers = null;

	protected void initProvider() {
		providers = new LinkedList<>();

		Iterator<FeedProvider> iterator = ServiceLoader.load(FeedProvider.class).iterator();
		while (iterator.hasNext()) {
			FeedProvider provider = iterator.next();
			providers.add(provider);
		}
	}

	/**
	 * Type format is: superType/subType/subType1/subType2[...]
	 * for example: rss/cafef.vn/Tin-moi.rss
	 * @param type
	 * @return
	 */
	protected FeedProvider findProvider(String type) {
		if(providers == null) {
			initProvider();
		}
		while (type != null && !type.isEmpty()) {
			for(FeedProvider provider : providers) {
				for(String supportedType : provider.getSupportedTypes()) {
					if(supportedType.equals(type)) {
						return provider;
					}
				}
			}

			if("*".equals(type)) {
				type = "";
			} else {
				int index = type.lastIndexOf('/');
				if(index > 0) {
					type = type.substring(0, index);
				} else {
					type = "*";
				}
			}
		}

		return null;
	}

	public Feed read(InputStream input, String type) throws IOException, FeedException {
		if(input == null) {
			throw new FeedException("InputStream must not null");
		}

		FeedProvider provider = this.findProvider(type);
		if(provider != null) {
			return provider.read(input);
		}

		throw new FeedException("Can not found FeedProvider for type: " + type);
	}
}
