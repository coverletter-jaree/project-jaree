import React from 'react';

const env: ImportMetaEnv = import.meta.env;

const EnvInfo: React.FC = () => {
  return (
    <div className="p-4 bg-gray-100 rounded-lg">
      <h3 className="text-lg font-semibold mb-2">환경변수 정보</h3>
      <div className="space-y-2">
        <div>
          <span className="font-medium">API Base URL:</span>{' '}
          {env.VITE_API_BASE_URL}
        </div>
        <div>
          <span className="font-medium">App Name:</span> {env.VITE_APP_NAME}
        </div>
        <div>
          <span className="font-medium">Debug Mode:</span> {env.VITE_DEBUG}
        </div>
        <div>
          <span className="font-medium">Log Level:</span> {env.VITE_LOG_LEVEL}
        </div>
        <div>
          <span className="font-medium">Build Time:</span> {env.__BUILD_TIME__}
        </div>
        <div>
          <span className="font-medium">App Version:</span>{' '}
          {env.__APP_VERSION__}
        </div>
      </div>
    </div>
  );
};

export default EnvInfo;
