(function() {
    'use strict';
    angular
        .module('utmApp')
        .factory('DiasLaborales', DiasLaborales);

    DiasLaborales.$inject = ['$resource'];

    function DiasLaborales ($resource) {
        var resourceUrl =  'api/dias-laborales/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
