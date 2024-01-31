import React, { useEffect, useRef } from 'react';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import Map from '../assets/mmap.png' 
import Icon from '../assets/location-icon-png-4237.png'

const NonGeographicMap = ( { locations } ) => {
  const mapRef = useRef(null);

  useEffect(() => {
    const bounds = [[-18, -18], [3, 3]];

    const map = L.map(mapRef.current, {
      crs: L.CRS.Simple,
      minZoom: 3,
      maxZoom: 8,
    });

    L.imageOverlay(Map, bounds).addTo(map);

    map.fitBounds(bounds);
    map.setMaxBounds(L.latLngBounds([bounds[0][1]-7, bounds[0][0]-7], [bounds[1][1]+7, bounds[1][0]+7]));

    
    const customIcon = L.icon({
      iconUrl: Icon, 
      iconSize: [20, 20], 
      iconAnchor: [20, 20], 
    }); 

    if (locations && locations.length > 0) {
      locations.forEach((location) => {
        const imageSizeStyle = 'max-width: 60px; max-height: 40px;';
        const { latitude, longitude, name, img } = location;
        const popupContent = `<p>${name}</p><img src="${img}" style="${imageSizeStyle}" />`;
        L.marker([latitude, longitude], { icon: customIcon, dragable: true }).addTo(map).bindPopup(popupContent);
      })
    }
    
    return () => {
      map.remove();
    };
  }, [locations])

  return (
    <div
      style={{
        position: 'relative',
        top: '10px',
        bottom: 'flex',
        left: '60px',
        width: '70%',
        height: '55vh',
        border: '10px solid #ccc',
        borderRadius: '10px',
        overflow: 'hidden',
        marginLeft: '7vh',
        marginTop: '50px',
        marginBottom: '50px',
      }}
    >
      <div id="map" style={{ position: 'center',height: '100%', width: '100%',}} ref={mapRef}></div>
    </div>
  );
};

export default NonGeographicMap;
