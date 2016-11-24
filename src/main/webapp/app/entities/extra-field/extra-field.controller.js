(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('ExtraFieldController', ExtraFieldController);

    ExtraFieldController.$inject = ['$scope', '$state', 'ExtraField'];

    function ExtraFieldController ($scope, $state, ExtraField) {
        var vm = this;

        vm.extraFields = [];

        loadAll();

        function loadAll() {
            ExtraField.query(function(result) {
                vm.extraFields = result;
            });
        }
    }
})();
