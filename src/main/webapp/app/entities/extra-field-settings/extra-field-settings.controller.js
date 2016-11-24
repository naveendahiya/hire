(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('ExtraFieldSettingsController', ExtraFieldSettingsController);

    ExtraFieldSettingsController.$inject = ['$scope', '$state', 'ExtraFieldSettings'];

    function ExtraFieldSettingsController ($scope, $state, ExtraFieldSettings) {
        var vm = this;

        vm.extraFieldSettings = [];

        loadAll();

        function loadAll() {
            ExtraFieldSettings.query(function(result) {
                vm.extraFieldSettings = result;
            });
        }
    }
})();
