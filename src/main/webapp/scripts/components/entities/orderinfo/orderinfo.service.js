'use strict';

angular.module('warehouseApp')
    .factory('Orderinfo', function ($resource, DateUtils) {
        return $resource('api/orderinfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.datecreate = DateUtils.convertDateTimeFromServer(data.datecreate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
