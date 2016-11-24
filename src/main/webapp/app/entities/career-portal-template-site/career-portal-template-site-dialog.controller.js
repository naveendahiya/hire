(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CareerPortalTemplateSiteDialogController', CareerPortalTemplateSiteDialogController);

    CareerPortalTemplateSiteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CareerPortalTemplateSite'];

    function CareerPortalTemplateSiteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CareerPortalTemplateSite) {
        var vm = this;

        vm.careerPortalTemplateSite = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.careerPortalTemplateSite.id !== null) {
                CareerPortalTemplateSite.update(vm.careerPortalTemplateSite, onSaveSuccess, onSaveError);
            } else {
                CareerPortalTemplateSite.save(vm.careerPortalTemplateSite, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:careerPortalTemplateSiteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
